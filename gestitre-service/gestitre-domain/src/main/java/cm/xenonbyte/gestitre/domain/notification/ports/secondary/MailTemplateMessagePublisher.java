package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public interface MailTemplateMessagePublisher {
    void publish(MailTemplateEvent event, MailTemplateEventType type);
}
