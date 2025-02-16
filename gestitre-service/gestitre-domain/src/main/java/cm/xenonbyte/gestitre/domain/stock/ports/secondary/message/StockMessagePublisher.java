package cm.xenonbyte.gestitre.domain.stock.ports.secondary.message;

import cm.xenonbyte.gestitre.domain.stock.event.StockMoveEvent;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMessagePublisher {
    void publish(StockMoveEvent event, StockMoveEventType type);
}
