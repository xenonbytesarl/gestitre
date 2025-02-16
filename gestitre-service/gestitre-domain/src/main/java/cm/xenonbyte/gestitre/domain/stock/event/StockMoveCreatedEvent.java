package cm.xenonbyte.gestitre.domain.stock.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@DomainEvent
public final class StockMoveCreatedEvent extends StockMoveEvent {

    public StockMoveCreatedEvent(StockMove stockMove, ZonedDateTime createdAt) {
        super(stockMove, createdAt);
    }
}
