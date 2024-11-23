package cm.xenonbyte.gestitre.infrastructure.tenant;

import cm.xenonbyte.gestitre.domain.company.vo.TenantEventType;
import cm.xenonbyte.gestitre.domain.tenant.TenantEvent;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.DefaultEventBus;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class TenantMessagePublisherAdapter implements TenantMessagePublisher {

    @Inject
    @DefaultEventBus
    EventBus eventBus;



    @Override
    public void publish(TenantEvent event, TenantEventType type) {
        log.info("Publishing event {} for tenant with name {}  in the bus",
                type.name(), event.getTenant().getName().text().value());
        eventBus.publish(type.name(), event);
    }
}
