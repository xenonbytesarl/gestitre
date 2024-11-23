package cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message;

import cm.xenonbyte.gestitre.domain.company.vo.TenantEventType;
import cm.xenonbyte.gestitre.domain.tenant.TenantEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public interface TenantMessagePublisher {
    void publish(TenantEvent event, TenantEventType type);
}
