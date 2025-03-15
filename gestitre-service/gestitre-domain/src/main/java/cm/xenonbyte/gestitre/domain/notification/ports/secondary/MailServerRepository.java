package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 01/12/2024
 */
public interface MailServerRepository {
    @Nonnull MailServer create(@Nonnull MailServer mailServer);

    Boolean existByName(@Nonnull Name name);

    Optional<MailServer> findById(@Nonnull MailServerId mailServerId);

    @Nonnull PageInfo<MailServer> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);

    @Nonnull MailServer update(@Nonnull MailServerId mailServerId, @Nonnull MailServer newMailServer);

    Optional<MailServer> findByIsDefault();
}
