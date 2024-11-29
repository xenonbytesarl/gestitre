package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.admin.event.UserEvent;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@DomainEvent
public final class UserUpdatedEvent extends UserEvent {
    public UserUpdatedEvent(User user, ZonedDateTime createdAt) {
        super(user, createdAt);
    }
}
