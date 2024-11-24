package cm.xenonbyte.gestitre.domain.audit.ports.message.listener;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
public interface AuditMessageListener {

    void handle(CompanyCreatedEvent event);
    void handle(CompanyUpdatedEvent event);
    void handle(UserCreatedEvent event);
    void handle(TenantCreatedEvent event);
}
