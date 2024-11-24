package cm.xenonbyte.gestitre.domain.admin.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.admin.User;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
@DomainEvent
public final class UserCreatedEvent extends UserEvent {
    public UserCreatedEvent(User user, ZonedDateTime createAt) {
        super(user, createAt);
    }
}
