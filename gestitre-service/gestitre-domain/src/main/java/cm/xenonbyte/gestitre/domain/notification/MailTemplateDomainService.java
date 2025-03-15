package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailTemplateService;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailTemplateMessagePublisher;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailTemplateRepository;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.notification.event.MailTemplateEventType.MAIL_TEMPLATE_CREATED;
import static cm.xenonbyte.gestitre.domain.notification.event.MailTemplateEventType.MAIL_TEMPLATE_UPDATED;
import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@DomainService
public class MailTemplateDomainService implements MailTemplateService {

    private final static Logger LOGGER = Logger.getLogger(MailTemplateDomainService.class.getName());
    private final MailTemplateRepository mailTemplateRepository;
    private final MailTemplateMessagePublisher mailTemplateMessagePublisher;

    public MailTemplateDomainService(MailTemplateRepository mailTemplateRepository, MailTemplateMessagePublisher mailTemplateMessagePublisher) {
        this.mailTemplateRepository = requireNonNull(mailTemplateRepository);
        this.mailTemplateMessagePublisher = requireNonNull(mailTemplateMessagePublisher);
    }

    @Nonnull
    @Override
    public MailTemplateCreatedEvent createMailTemplate(@Nonnull MailTemplate mailTemplate) {
        mailTemplate.validateMandatoryFields();
        validateMailTemplate(mailTemplate);
        mailTemplate.initializeDefaults();
        mailTemplate = mailTemplateRepository.save(mailTemplate);
        LOGGER.info("Mail template is created with id " + mailTemplate.getId().getValue());
        MailTemplateCreatedEvent mailTemplateCreatedEvent = new MailTemplateCreatedEvent(mailTemplate, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        mailTemplateMessagePublisher.publish(mailTemplateCreatedEvent, MAIL_TEMPLATE_CREATED);
        return mailTemplateCreatedEvent;
    }

    @Nonnull
    @Override
    public MailTemplate findMailTemplateByType(@Nonnull MailTemplateType type) {
        return mailTemplateRepository.findByType(type).orElseThrow(
                () -> new MailTemplateTypeNotFoundException(new String[] {type.name()})
        );
    }

    @Nonnull
    @Override
    public MailTemplate findMailTemplateById(@Nonnull MailTemplateId mailTemplateId) {
        return mailTemplateRepository.findById(mailTemplateId).orElseThrow(
                () -> new MailTemplateNotFoundException(new String[] {mailTemplateId.getValue().toString()})
        );
    }

    @Nonnull
    @Override
    public MailTemplateUpdatedEvent updateMailTemplate(@Nonnull MailTemplateId mailTemplateId, @Nonnull MailTemplate newMailTemplate) {
        newMailTemplate.validateMandatoryFields();
        findMailTemplateById(mailTemplateId);
        validateMailTemplate(newMailTemplate);
        newMailTemplate = mailTemplateRepository.update(mailTemplateId, newMailTemplate);
        LOGGER.info("Mail template is updated with id " + mailTemplateId.getValue());
        MailTemplateUpdatedEvent mailTemplateUpdatedEvent = new MailTemplateUpdatedEvent(newMailTemplate, ZonedDateTime.now());
        mailTemplateMessagePublisher.publish(mailTemplateUpdatedEvent, MAIL_TEMPLATE_UPDATED);
        return mailTemplateUpdatedEvent;
    }

    @Nonnull
    @Override
    public PageInfo<MailTemplate> searchMailTemplate(@Nonnull PageInfoPage page, PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword) {
        PageInfo.validatePageParameters(page, size, field, direction);
        Assert.field("Keyword", keyword)
                .notNull();
        return mailTemplateRepository.search(page, size, field, direction, keyword);
    }

    private void validateMailTemplate(MailTemplate mailTemplate) {
        validateName(mailTemplate.getId(), mailTemplate.getName());
        validateType(mailTemplate.getId(), mailTemplate.getType());
    }

    private void validateType(MailTemplateId mailTemplateId, MailTemplateType type) {
        if(mailTemplateId == null && mailTemplateRepository.existsByType(type)) {
            throw new MailTemplateTypeConflictException(new String[] {type.name()});
        }

        Optional<MailTemplate> oldMailTemplate = mailTemplateRepository.findByType(type);
        if(mailTemplateId != null && oldMailTemplate.isPresent() && !oldMailTemplate.get().getId().equals(mailTemplateId)) {
            throw new MailTemplateTypeConflictException(new String[] {type.name()});
        }
    }

    private void validateName(MailTemplateId mailTemplateId, Name name) {
        if(mailTemplateId == null && mailTemplateRepository.existsByName(name)) {
            throw new MailTemplateNameConflictException(new String[] {name.text().value()});
        }

        Optional<MailTemplate> oldMailTemplate = mailTemplateRepository.findByName(name);
        if(mailTemplateId != null && oldMailTemplate.isPresent() && !oldMailTemplate.get().getId().equals(mailTemplateId)) {
            throw new MailTemplateNameConflictException(new String[] {name.text().value()});
        }
    }
}
