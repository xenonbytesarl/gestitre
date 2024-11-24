package cm.xenonbyte.gestitre.domain.security;

import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher.UserEventType;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.security.vo.UserId;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public final class UserMessageInMemoryPublisher implements UserMessagePublisher {

    private Map<UserId, User> users = new LinkedHashMap<>();

    @Override
    public void publish(UserCreatedEvent event, UserEventType type) {
        users.put(event.getUser().getId(), event.getUser());
    }
}
