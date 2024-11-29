package cm.xenonbyte.gestitre.domain.notification.ports.secondary.message;

import cm.xenonbyte.gestitre.domain.notification.event.MailServerEvent;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 01/12/2024
 */
public interface MailServerMessagePublisher {
    void publish(MailServerEvent event, MailServerEventType type);
}
