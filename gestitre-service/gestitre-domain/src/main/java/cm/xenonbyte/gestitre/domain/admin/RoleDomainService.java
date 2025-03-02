package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.admin.ports.primary.RoleService;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import jakarta.annotation.Nonnull;

import java.util.Objects;

import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.validatePageParameters;

/**
 * @author bamk
 * @version 1.0
 * @since 02/03/2025
 */
@DomainService
public final class RoleDomainService implements RoleService {

    private final RoleRepository roleRepository;

    public RoleDomainService(@Nonnull RoleRepository roleRepository) {
        this.roleRepository = Objects.requireNonNull(roleRepository);
    }

    @Nonnull
    @Override
    public PageInfo<Role> searchRoles(PageInfoPage pageInfoPage, PageInfoSize pageInfoSize, PageInfoField pageInfoField, PageInfoDirection pageInfoDirection, Keyword keyword) {
        validatePageParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        Assert.field("Keyword", keyword).notNull();
        return roleRepository.search(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
    }
}
