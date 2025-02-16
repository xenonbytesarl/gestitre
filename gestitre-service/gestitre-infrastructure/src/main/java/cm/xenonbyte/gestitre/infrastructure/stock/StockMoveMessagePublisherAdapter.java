package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.domain.stock.event.StockMoveEvent;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveEventType;
import cm.xenonbyte.gestitre.domain.stock.ports.secondary.message.StockMessagePublisher;
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
public final class StockMoveMessagePublisherAdapter implements StockMessagePublisher {

    private final EventBus eventBus;

    public StockMoveMessagePublisherAdapter(@Nonnull EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void publish(StockMoveEvent event, StockMoveEventType type) {
        log.info("Publishing event {} for stock move with reference {}  in the bus",
                type.name(), event.getStockMove().getReference().text().value());
        eventBus.publish(type.name(), event);
    }
}
