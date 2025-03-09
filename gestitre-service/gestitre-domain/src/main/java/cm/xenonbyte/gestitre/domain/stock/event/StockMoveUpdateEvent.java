package cm.xenonbyte.gestitre.domain.stock.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 09/03/2025
 */
@DomainEvent
public final class StockMoveUpdateEvent extends StockMoveEvent{
    public StockMoveUpdateEvent(StockMove stockMove, ZonedDateTime createdAt) {
        super(stockMove, createdAt);
    }
}
