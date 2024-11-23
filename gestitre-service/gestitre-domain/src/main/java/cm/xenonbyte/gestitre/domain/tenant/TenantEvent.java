package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public abstract class TenantEvent implements BaseEvent<Tenant> {
    protected final Tenant tenant;
    protected final ZonedDateTime createdAt;

    protected TenantEvent(Tenant tenant, ZonedDateTime createdAt) {
        this.tenant = tenant;
        this.createdAt = createdAt;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
