package cm.xenonbyte.gestitre.domain.admin.adapter;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.event.UserEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.vo.UserEventType;
import cm.xenonbyte.gestitre.domain.admin.vo.UserId;

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
    public void publish(UserEvent event, UserEventType type) {
        users.put(event.getUser().getId(), event.getUser());
    }
}
