package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.notification.event.MailServerEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.message.MailServerMessagePublisher;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerEventType;
import cm.xenonbyte.gestitre.domain.tenant.TenantContext;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Slf4j
@ApplicationScoped
public final class MailServerMessagePublisherAdapter implements MailServerMessagePublisher {

    private final EventBus eventBus;

    public MailServerMessagePublisherAdapter(@Nonnull EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Override
    public void publish(MailServerEvent event, MailServerEventType type) {
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("tenantId", TenantContext.current().toString());
        log.info("Publishing event {} for mail server with name {}  in the bus",
                type.name(), event.getMailServer().getName().text().value());
        eventBus.publish(type.name(), event, options);
    }
}
