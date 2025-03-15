package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.notification.event.MailEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public interface MailMessagePublisher {
    void publish(MailEvent event, MailEventType type);
}
