package cm.xenonbyte.gestitre.domain.admin.adapter;

import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
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

    @Nonnull
    @Override
    public PageInfo<Role> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword) {
        return null;
    }
}
