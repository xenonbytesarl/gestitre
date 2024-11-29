package cm.xenonbyte.gestitre.domain.admin.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface UserService {
    @Nonnull UserCreatedEvent createUser(@Nonnull User user);

    @Nonnull User login(@Nonnull Email email, @Nonnull Password password);

    @Nonnull Token generateToken(@Nonnull User user);

    @Nonnull User activateUser(User user);
}
