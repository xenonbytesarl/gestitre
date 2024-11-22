package cm.xenonbyte.gestitre.domain.security.ports.secondary;

import cm.xenonbyte.gestitre.domain.security.Role;
import cm.xenonbyte.gestitre.domain.security.vo.RoleId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface RoleRepository {
    Boolean existsById(@Nonnull RoleId roleId);

    @Nonnull Role create(@Nonnull Role role);
}
