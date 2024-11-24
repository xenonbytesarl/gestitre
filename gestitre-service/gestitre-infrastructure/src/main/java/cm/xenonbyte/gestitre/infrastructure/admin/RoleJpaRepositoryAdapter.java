package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.security.Role;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.security.vo.RoleId;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class RoleJpaRepositoryAdapter implements RoleRepository {

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
}
