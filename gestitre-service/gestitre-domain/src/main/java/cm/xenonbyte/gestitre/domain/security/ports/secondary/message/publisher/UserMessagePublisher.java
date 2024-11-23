package cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher;

import cm.xenonbyte.gestitre.domain.security.event.UserEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface UserMessagePublisher {
    void publish(UserEvent event, UserEventType type);
}
