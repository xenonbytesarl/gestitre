package cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public interface TenantMessageListener {
    void handle(CompanyCreatedEvent event);
}
