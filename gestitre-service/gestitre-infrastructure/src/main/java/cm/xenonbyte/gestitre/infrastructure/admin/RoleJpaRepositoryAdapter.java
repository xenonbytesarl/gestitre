package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.stream.Collectors;

import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.computeOderBy;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class RoleJpaRepositoryAdapter implements RoleRepository {

    private static final String ROLE_SEARCH_BY_KEYWORD_QUERY =
            "select r from RoleJpa r where lower(concat(r.name, '')) like lower(?1) order by r.";

    private final RoleJpaRepository roleJpaRepository;
    private final RoleJpaMapper roleJpaMapper;

    public RoleJpaRepositoryAdapter(
            @Nonnull RoleJpaRepository roleJpaRepository,
            @Nonnull RoleJpaMapper roleJpaMapper) {
        this.roleJpaRepository = Objects.requireNonNull(roleJpaRepository);
        this.roleJpaMapper = Objects.requireNonNull(roleJpaMapper);
    }

    @Override
    public Boolean existsById(@Nonnull RoleId roleId) {
        return roleJpaRepository.findByIdOptional(roleId.getValue()).isPresent();
    }

    @Nonnull
    @Override
    @Transactional
    public Role create(@Nonnull Role role) {
        roleJpaRepository.persist(roleJpaMapper.toRoleJpa(role));
        return role;
    }

    @Nonnull
    @Override
    public PageInfo<Role> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword) {
        PanacheQuery<RoleJpa> queryResult =
                roleJpaRepository.find(ROLE_SEARCH_BY_KEYWORD_QUERY + computeOderBy(pageInfoField, pageInfoDirection), keyword.toLikeKeyword());
        PanacheQuery<RoleJpa> rolePageQueryResult = queryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));
        return new PageInfo<>(
                !rolePageQueryResult.hasPreviousPage(),
                !rolePageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                rolePageQueryResult.count(),
                rolePageQueryResult.pageCount(),
                rolePageQueryResult
                        .list()
                        .stream()
                        .map(roleJpaMapper::toRole)
                        .collect(Collectors.toSet())
                        .stream().toList()
        );
    }
}
