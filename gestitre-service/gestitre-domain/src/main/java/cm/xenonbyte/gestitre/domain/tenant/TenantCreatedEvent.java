package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
@DomainEvent
public final class TenantCreatedEvent extends TenantEvent {
    public TenantCreatedEvent(Tenant tenant, ZonedDateTime createdAt) {
        super(tenant, createdAt);
    }
}
