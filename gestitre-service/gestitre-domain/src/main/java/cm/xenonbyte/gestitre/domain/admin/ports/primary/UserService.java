package cm.xenonbyte.gestitre.domain.admin.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface UserService {
    @Nonnull UserCreatedEvent createUser(@Nonnull User user) throws Exception;

}
