package cm.xenonbyte.gestitre.domain.security.ports.secondary;

import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.security.User;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface UserRepository {
    Boolean existsByEmail(@Nonnull Email email);

    User create(@Nonnull User user);
}
