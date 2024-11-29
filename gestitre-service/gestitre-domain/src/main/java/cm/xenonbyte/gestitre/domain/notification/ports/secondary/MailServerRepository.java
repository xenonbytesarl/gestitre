package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
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

    @Nonnull MailServer update(@Nonnull MailServerId mailServerId, @Nonnull MailServer newMailServer);
}
