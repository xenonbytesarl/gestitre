package cm.xenonbyte.gestitre.domain.common.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.entity.Tenant;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface TenantRepository {
    Boolean existsById(@Nonnull TenantId tenantId);

    @Nonnull Tenant create(@Nonnull Tenant tenant);

    Optional<Tenant> findByName(Name name);
}
