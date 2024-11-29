package cm.xenonbyte.gestitre.domain.notification.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerConfirmedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerUpdatedEvent;
import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import jakarta.annotation.Nonnull;

/**
* @author bamk
* @version 1.0
* @since 29/11/2024
*/public interface MailServerService {
    @Nonnull MailServerCreatedEvent createMailServer(@Nonnull MailServer mailServer);
    @Nonnull
    MailServerUpdatedEvent updateMailServer(@Nonnull MailServerId mailServerId, @Nonnull MailServer newMailServer);
    @Nonnull
    MailServerConfirmedEvent confirmMailServer(@Nonnull MailServerId mailServerId, @Nonnull Code code);
    @Nonnull MailServer findMailServerById(@Nonnull MailServerId mailServerId);
    PageInfo<MailServer> findMailServers(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction);
    PageInfo<MailServer> searchMailServers(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);
}
