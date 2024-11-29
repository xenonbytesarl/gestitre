package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.notification.MailServer;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/12/2024
 */
public class MailServerEvent implements BaseEvent<MailServer> {
    private final MailServer mailServer;
    private final ZonedDateTime createdAt;


    public MailServerEvent(MailServer mailServer, ZonedDateTime createdAt) {
        this.mailServer = Objects.requireNonNull(mailServer);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public MailServer getMailServer() {
        return mailServer;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
