package cm.xenonbyte.gestitre.domain.company.addapter.inmemory;

import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.TenantEventType;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.TenantEvent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public final class TenantInMemoryMessagePublisher implements TenantMessagePublisher {

    private Map<TenantId, Tenant> tenants = new LinkedHashMap<>();

    @Override
    public void publish(TenantEvent event, TenantEventType type) {
        tenants.put(event.getTenant().getId(), event.getTenant());
    }
}
