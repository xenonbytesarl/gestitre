package cm.xenonbyte.gestitre.infrastructure.tenant;

import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class TenantJpaRepositoryAdapter implements TenantRepository {

    private final TenantJpaRepository tenantJpaRepository;
    private final TenantJpaMapper tenantJpaMapper;

    public TenantJpaRepositoryAdapter(
            @Nonnull final TenantJpaRepository tenantJpaRepository,
            @Nonnull final TenantJpaMapper tenantJpaMapper
    ) {
        this.tenantJpaRepository = Objects.requireNonNull(tenantJpaRepository);
        this.tenantJpaMapper = Objects.requireNonNull(tenantJpaMapper);
    }

    @Override
    public Boolean existsById(@Nonnull TenantId tenantId) {
        return tenantJpaRepository.findByIdOptional(tenantId.getValue()).isPresent();
    }

    @Nonnull
    @Override
    @Transactional
    public Tenant create(@Nonnull Tenant tenant) {
        tenantJpaRepository.persist(tenantJpaMapper.toTenantJpa(tenant));
        return tenantJpaMapper.toTenant(
                tenantJpaRepository.findById(tenant.getId().getValue())
        );
    }

    @Override
    public Optional<Tenant> findByName(Name name) {
        return tenantJpaRepository.findByName(name.text().value())
                .map(tenantJpaMapper::toTenant);
    }

    @Override
    public Boolean existsByName(@Nonnull Name name) {
        return tenantJpaRepository.existsByName(name.text().value());
    }
}
