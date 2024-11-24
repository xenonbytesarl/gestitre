package cm.xenonbyte.gestitre.infrastructure.tenant;

import cm.xenonbyte.gestitre.domain.company.vo.TenantEventType;
import cm.xenonbyte.gestitre.domain.tenant.TenantEvent;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.TenantMessagePublisher;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class TenantMessagePublisherAdapter implements TenantMessagePublisher {

    private final EventBus eventBus;

    public TenantMessagePublisherAdapter(@Nonnull  EventBus eventBus) {
        this.eventBus = Objects.requireNonNull(eventBus);
    }


    @Override
    public void publish(TenantEvent event, TenantEventType type) {
        log.info("Publishing event {} for tenant with name {}  in the bus",
                type.name(), event.getTenant().getName().text().value());
        eventBus.publish(type.name(), event);
    }
}
