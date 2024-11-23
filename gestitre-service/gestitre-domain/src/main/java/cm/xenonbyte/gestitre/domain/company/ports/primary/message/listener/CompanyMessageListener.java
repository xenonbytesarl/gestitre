package cm.xenonbyte.gestitre.domain.company.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.tenant.TenantCreateEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public interface CompanyMessageListener {
    void handle(TenantCreateEvent event);
}
