package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.event.UserEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.vo.UserEventType;
import cm.xenonbyte.gestitre.domain.context.TenantContext;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class UserMessagePublisherAdapter implements UserMessagePublisher {

    private final EventBus eventBus;

    public UserMessagePublisherAdapter(@Nonnull  EventBus eventBus) {
        this.eventBus = Objects.requireNonNull(eventBus);
    }

    @Override
    public void publish(UserEvent event, UserEventType type) {
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("tenantId", TenantContext.current().toString());
        log.info("Publishing event {} for user with name {}  in the bus",
                type.name(), event.getUser().getName().text().value());
        eventBus.publish(type.name(), event, options);
    }
}
