package cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreateEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface TenantService {

    TenantCreateEvent create(Tenant tenant);
}
