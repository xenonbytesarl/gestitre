package cm.xenonbyte.gestitre.infrastructure.tenant;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@ApplicationScoped
public final class TenantJpaRepository implements PanacheRepositoryBase<TenantJpa, UUID> {
    public Optional<TenantJpa> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public Boolean existsByName(String name) {
        return find("name", name).count() > 0;
    }

    public Boolean existsByCode(String code) {
        return find("code", code).count() > 0;
    }

    public Optional<TenantJpa> findByCode(String code) {
        return find("code", code).firstResultOptional();
    }
}
