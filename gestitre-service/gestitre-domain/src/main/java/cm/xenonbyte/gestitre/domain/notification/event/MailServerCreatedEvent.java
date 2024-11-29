package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.notification.MailServer;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 01/12/2024
 */
@DomainEvent
public final class MailServerCreatedEvent extends MailServerEvent {
    public MailServerCreatedEvent(MailServer mailServer, ZonedDateTime createdAt) {
        super(mailServer, createdAt);
    }
}
