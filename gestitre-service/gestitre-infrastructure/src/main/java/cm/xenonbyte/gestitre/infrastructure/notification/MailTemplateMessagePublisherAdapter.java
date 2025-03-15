package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.context.TenantContext;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateEventType;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailTemplateMessagePublisher;
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
public final class MailTemplateMessagePublisherAdapter implements MailTemplateMessagePublisher {
    private final EventBus eventBus;

    public MailTemplateMessagePublisherAdapter(@Nonnull EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Override
    public void publish(MailTemplateEvent event, MailTemplateEventType type) {
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("tenantId", TenantContext.current().toString());
        log.info("Publishing event {} for mail template with name {}  in the bus",
                type.name(), event.getMailTemplate().getName().text().value());
        eventBus.publish(type.name(), event, options);
    }
}
