package cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher;

import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface UserMessagePublisher {
    void publish(UserCreatedEvent event, UserEventType type);
}
