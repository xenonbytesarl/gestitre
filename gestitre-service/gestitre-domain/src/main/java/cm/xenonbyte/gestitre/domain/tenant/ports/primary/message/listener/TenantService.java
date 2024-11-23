package cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface TenantService {

    TenantCreatedEvent create(Tenant tenant);
}
