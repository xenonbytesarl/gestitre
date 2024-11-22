package cm.xenonbyte.gestitre.domain.security.adapter;

import cm.xenonbyte.gestitre.domain.security.Role;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.security.vo.RoleId;
import jakarta.annotation.Nonnull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class RoleInMemoryRepository implements RoleRepository {

    private Map<RoleId, Role> roles = new LinkedHashMap<>();

    @Override
    public Boolean existsById(@Nonnull RoleId roleId) {
        return roles.containsKey(roleId);
    }

    @Nonnull
    @Override
    public Role create(@Nonnull Role role) {
        roles.put(role.getId(), role);
        return role;
    }
}
