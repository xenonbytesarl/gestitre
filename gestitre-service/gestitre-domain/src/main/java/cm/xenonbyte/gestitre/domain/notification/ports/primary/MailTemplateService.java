package cm.xenonbyte.gestitre.domain.notification.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public interface MailTemplateService {

    @Nonnull MailTemplateCreatedEvent createMailTemplate(@Nonnull MailTemplate mailTemplate);

    @Nonnull MailTemplate findMailTemplateByType(@Nonnull MailTemplateType type);

    @Nonnull MailTemplate findMailTemplateById(@Nonnull MailTemplateId mailTemplateId);

    @Nonnull MailTemplateUpdatedEvent updateMailTemplate(@Nonnull MailTemplateId mailTemplateId, @Nonnull MailTemplate newMailTemplate);

    @Nonnull PageInfo<MailTemplate> searchMailTemplate(@Nonnull PageInfoPage page, PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);
}
