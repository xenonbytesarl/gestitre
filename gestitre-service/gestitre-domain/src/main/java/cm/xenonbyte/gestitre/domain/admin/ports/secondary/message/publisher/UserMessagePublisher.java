package cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher;

import cm.xenonbyte.gestitre.domain.admin.event.UserEvent;
import cm.xenonbyte.gestitre.domain.admin.vo.UserEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface UserMessagePublisher {
    void publish(UserEvent event, UserEventType type);
}
