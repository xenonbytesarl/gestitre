package cm.xenonbyte.gestitre.infrastructure.shareholder;

import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderEventType;
import cm.xenonbyte.gestitre.domain.shareholder.ports.secondary.message.ShareHolderMessagePublisher;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@Slf4j
@ApplicationScoped
public final class ShareHolderMessagePublisherAdapter implements ShareHolderMessagePublisher {

    private final EventBus eventBus;

    public ShareHolderMessagePublisherAdapter(@Nonnull EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void publish(ShareHolderEvent event, ShareHolderEventType type) {
        log.info("Publishing event {} for share holder with name {}  in the bus",
                type.name(), event.getShareHolder().getName().text().value());
        eventBus.publish(type.name(), event);
    }
}
