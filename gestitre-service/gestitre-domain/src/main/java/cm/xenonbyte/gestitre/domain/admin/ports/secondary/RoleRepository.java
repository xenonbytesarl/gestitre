package cm.xenonbyte.gestitre.domain.admin.ports.secondary;

import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
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
