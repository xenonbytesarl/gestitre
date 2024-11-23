package cm.xenonbyte.gestitre.infrastructure.Tenant;

import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.TenantEventType;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.TenantEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class TenantMessagePublisherAdapter implements TenantMessagePublisher {


    private final EventBus bus;

    public TenantMessagePublisherAdapter(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void publish(TenantEvent event, TenantEventType type) {
        log.info("Publishing event {} for tenant with name {}  in the bus",
                type.name(), event.getTenant().getName().text().value());
        bus.publish(type.name(), event);
    }
}
