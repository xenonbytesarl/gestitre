package cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface TenantService {

    TenantCreatedEvent create(Tenant tenant);

    Tenant findByName(@Nonnull Name name);

    Tenant findTenantById(@Nonnull TenantId tenantId);

    Tenant findByCode(@Nonnull Code code);
}
