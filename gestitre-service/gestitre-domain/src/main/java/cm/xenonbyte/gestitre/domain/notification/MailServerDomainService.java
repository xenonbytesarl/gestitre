package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerConfirmedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailServerService;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailServerRepository;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.message.MailServerMessagePublisher;
import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.notification.vo.MailServerEventType.MAIL_SERVER_CONFIRMED;
import static cm.xenonbyte.gestitre.domain.notification.vo.MailServerEventType.MAIL_SERVER_CREATED;
import static cm.xenonbyte.gestitre.domain.notification.vo.MailServerEventType.MAIL_SERVER_UPDATED;

/**
 * @author bamk
 * @version 1.0
 * @since 01/12/2024
 */
@DomainService
public final class MailServerDomainService implements MailServerService {

    private static final Logger LOGGER = Logger.getLogger(MailServerDomainService.class.getName());
    private final MailServerRepository mailServerRepository;
    private final MailServerMessagePublisher mailServerMessagePublisher;
    private final VerificationService verificationService;

    public MailServerDomainService(
            @Nonnull MailServerRepository mailServerRepository,
            @Nonnull MailServerMessagePublisher mailServerMessagePublisher,
            @Nonnull VerificationService verificationService
    ) {
        this.mailServerRepository = Objects.requireNonNull(mailServerRepository);
        this.mailServerMessagePublisher = Objects.requireNonNull(mailServerMessagePublisher);
        this.verificationService = Objects.requireNonNull(verificationService);
    }

    @Nonnull
    @Override
    public MailServerCreatedEvent createMailServer(@Nonnull MailServer mailServer) {
        mailServer.validateMandatoryFields();
        validateMailServer(mailServer);
        mailServer.initializeDefaultValues();
        mailServerRepository.create(mailServer);
        LOGGER.info("Mail server is created with id " + mailServer.getId().getValue());
        MailServerCreatedEvent mailServerCreatedEvent = new MailServerCreatedEvent(mailServer, ZonedDateTime.now());
        mailServerMessagePublisher.publish(mailServerCreatedEvent, MAIL_SERVER_CREATED);
        return mailServerCreatedEvent;
    }


    @Nonnull
    @Override
    public MailServerUpdatedEvent updateMailServer(@Nonnull MailServerId mailServerId, @Nonnull MailServer newMailServer) {
        newMailServer.validateMandatoryFields();
        findMailServerById(mailServerId);
        validateMailServer(newMailServer);
        newMailServer = mailServerRepository.update(mailServerId, newMailServer);
        LOGGER.info("Mail server is updated with id " + mailServerId.getValue());
        MailServerUpdatedEvent mailServerUpdatedEvent = new MailServerUpdatedEvent(newMailServer, ZonedDateTime.now());
        mailServerMessagePublisher.publish(mailServerUpdatedEvent, MAIL_SERVER_UPDATED);
        return mailServerUpdatedEvent;
    }

    @Nonnull
    @Override
    public MailServerConfirmedEvent confirmMailServer(@Nonnull MailServerId mailServerId, @Nonnull Code code) {
        Optional<MailServer> optionalMailServer = mailServerRepository.findById(mailServerId);
        if (optionalMailServer.isPresent()) {
            MailServer mailServer = optionalMailServer.get();
            verificationService.verifyCode(code);
            mailServer.confirm();
            mailServerRepository.update(mailServer.getId(), mailServer);
            LOGGER.info("Mail server confirmed ");
            MailServerConfirmedEvent mailServerConfirmedEvent = new MailServerConfirmedEvent(mailServer, ZonedDateTime.now());
            mailServerMessagePublisher.publish(mailServerConfirmedEvent, MAIL_SERVER_CONFIRMED);
            return mailServerConfirmedEvent;
        }
        throw new MailServerNotFoundException(new String[] {mailServerId.getValue().toString()});
    }

    @Nonnull
    @Override
    public MailServer findMailServerById(@Nonnull MailServerId mailServerId) {
        return mailServerRepository.findById(mailServerId).orElseThrow(
                () -> new MailServerNotFoundException(new String[] {mailServerId.getValue().toString()})
        );
    }

    @Override
    public PageInfo<MailServer> findMailServers(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction) {
        return null;
    }

    @Override
    public PageInfo<MailServer> searchMailServers(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword) {
        return null;
    }

    private void validateMailServer(MailServer mailServer) {
        if(mailServerRepository.existByName(mailServer.getName())) {
            throw new MailServerNameConflictException(new String[] {mailServer.getName().text().value()});
        }
    }
}
