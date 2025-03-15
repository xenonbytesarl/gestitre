package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.notification.event.MailEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailEventType;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailMessagePublisher;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailMessagePublisherAdapter implements MailMessagePublisher {
    private final EventBus eventBus;

    public MailMessagePublisherAdapter(@Nonnull EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Override
    public void publish(MailEvent event, MailEventType type) {
        DeliveryOptions options = new DeliveryOptions();
        //options.addHeader("tenantId", TenantContext.current().toString());
        log.info("Publishing event {} for mail with id {}  in the bus",
                type.name(), event.getMail().getId().getValue());
        eventBus.publish(type.name(), event, options);
    }
}
