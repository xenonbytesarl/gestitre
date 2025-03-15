package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public interface MailTemplateRepository {

    @Nonnull MailTemplate save(@Nonnull MailTemplate mailTemplate);

    Optional<MailTemplate> findByType(@Nonnull MailTemplateType type);

    Boolean existsByType(@Nonnull MailTemplateType type);

    Boolean existsByName(@Nonnull Name name);

    Optional<MailTemplate> findByName(@Nonnull Name name);

    @Nonnull PageInfo<MailTemplate> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);

    Optional<MailTemplate> findById(@Nonnull MailTemplateId mailTemplateId);

    @Nonnull MailTemplate update(@Nonnull MailTemplateId mailTemplateId, @Nonnull MailTemplate newMailTemplate);
}
