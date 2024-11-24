package cm.xenonbyte.gestitre.domain.company.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public interface CompanyMessageListener {
    void handle(TenantCreatedEvent event);
    void handle(UserCreatedEvent event);
    void handle(CompanyUpdatedEvent event);
}
