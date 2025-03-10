package cm.xenonbyte.gestitre.domain.admin.event;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.admin.User;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public class UserEvent implements BaseEvent<User> {
    private final User user;
    private final ZonedDateTime createAt;

    public UserEvent(User user, ZonedDateTime createdAt) {
        this.user = user;
        this.createAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public ZonedDateTime getCreateAt() {
        return createAt;
    }
}
