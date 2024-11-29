package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.notification.MailServer;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@DomainEvent
public final class MailServerConfirmedEvent extends MailServerEvent {
    public MailServerConfirmedEvent(MailServer mailServer, ZonedDateTime createdAt) {
        super(mailServer, createdAt);
    }
}
