package cm.xenonbyte.gestitre.domain.notification;


import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainTechnicalException;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.event.MailCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailFailedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailSentEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailServerService;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailService;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailTemplateService;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailMessagePublisher;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailRepository;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailSenderService;
import cm.xenonbyte.gestitre.domain.notification.vo.MailId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailState;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.notification.Mail.MAX_ATTEMPT_TO_SEND;
import static cm.xenonbyte.gestitre.domain.notification.event.MailEventType.MAIL_CREATED;
import static cm.xenonbyte.gestitre.domain.notification.event.MailEventType.MAIL_FAIL;
import static cm.xenonbyte.gestitre.domain.notification.event.MailEventType.MAIL_SEND;
import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
@DomainService
public final class MailDomainService implements MailService {

    private static final Logger LOGGER = Logger.getLogger(MailDomainService.class.getName());

    private final MailTemplateService mailTemplateService;
    private final MailServerService mailServerService;
    private final MailRepository mailRepository;
    private final MailMessagePublisher mailMessagePublisher;
    private final MailSenderService mailSenderService;

    public MailDomainService(
            MailTemplateService mailTemplateService,
            MailServerService mailServerService,
            MailRepository mailRepository,
            MailMessagePublisher mailMessagePublisher,
            MailSenderService mailSenderService) {
        this.mailTemplateService = requireNonNull(mailTemplateService);
        this.mailServerService = requireNonNull(mailServerService);
        this.mailRepository = requireNonNull(mailRepository);
        this.mailMessagePublisher = requireNonNull(mailMessagePublisher);
        this.mailSenderService = requireNonNull(mailSenderService);
    }

    @Nonnull
    @Override
    public MailCreatedEvent createMail(@Nonnull Mail mail) {
        mail.validateMandatoryFields();
        MailTemplate mailTemplate = mailTemplateService.findMailTemplateByType(mail.getType());
        if(mail.getMailServerId() == null) {
            MailServer mailserver = mailServerService.findDefaultMailServer();
            mail.initializeWithDefaults(mailTemplate.getId(), mailserver.getId(), mailserver.getFrom());
        } else {
            MailServer mailServer = mailServerService.findMailServerById(mail.getMailServerId());
            mail.initializeWithDefaults(mailTemplate.getId(), null, mailServer.getFrom());
        }
        mail = mailRepository.save(mail);
        LOGGER.info("Mail created with id " + mail.getId().getValue());
        MailCreatedEvent mailCreatedEvent = new MailCreatedEvent(mail, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        mailMessagePublisher.publish(mailCreatedEvent, MAIL_CREATED);
        return mailCreatedEvent;
    }

    @Nonnull
    @Override
    public Mail findMailById(@Nonnull MailId mailId) {
        return mailRepository.findById(mailId).orElseThrow(
                () -> new MailNotFoundException(new String[] {mailId.getValue().toString()})
        );
    }

    @Nonnull
    @Override
    public PageInfo<Mail> searchMail(@Nonnull PageInfoPage page, PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword) {
        PageInfo.validatePageParameters(page, size, field, direction);
        Assert.field("Keyword", keyword)
                .notNull();
        return mailRepository.search(page, size, field, direction, keyword);
    }

    @Override
    public void sendMails() {
        LOGGER.info("Start Sending email in state SENDING...");
        mailRepository.findByState(MailState.SENDING).forEach(mail -> {
            if(mail.getAttemptToSend() != null && mail.getAttemptToSend().value().compareTo(MAX_ATTEMPT_TO_SEND) >= 0)  {
                sendMailFail(mail);
            } else {
                MailServer mailServer = mailServerService.findMailServerById(mail.getMailServerId());
                MailTemplate mailTemplate = mailTemplateService.findMailTemplateById(mail.getMailTemplateId());
                sendMailIncrementAttempt(mail);
                try {
                    mailSenderService.send(mail, mailTemplate, mailServer);
                    sendMailSuccess(mail);
                } catch (BaseDomainTechnicalException exception) {
                    sendMailFail(mail);
                    throw new SendMailTechnicalException(new String[] {exception.getMessage()});
                }
            }

        });
        LOGGER.info("Complete Sending email....");
    }

    private void sendMailSuccess(Mail mail) {
        mail.success();
        mailRepository.update(mail.getId(), mail);
        LOGGER.info("Success sending mail updated with with " + mail.getId().getValue());
        MailSentEvent mailSentEvent = new MailSentEvent(mail, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        mailMessagePublisher.publish(mailSentEvent, MAIL_SEND);
    }

    private void sendMailIncrementAttempt(Mail mail) {
        mail.incrementAttempt();
        mailRepository.update(mail.getId(), mail);
        LOGGER.info("Increment attempt to send for mail with id " + mail.getId().getValue());
    }

    private void sendMailFail(Mail mail) {
        mail.fail();
        if(mail.getState().equals(MailState.ERROR)) {
            mailRepository.update(mail.getId(), mail);
            LOGGER.info("" +
                    "+Failed sending updated with with " + mail.getId().getValue());
            MailFailedEvent mailFailedEvent = new MailFailedEvent(mail, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
            mailMessagePublisher.publish(mailFailedEvent, MAIL_FAIL);
        }

    }
}
