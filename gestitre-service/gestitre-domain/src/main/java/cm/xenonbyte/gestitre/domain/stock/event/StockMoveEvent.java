package cm.xenonbyte.gestitre.domain.stock.event;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public class StockMoveEvent implements BaseEvent<StockMove> {
    private final StockMove stockMove;
    private final ZonedDateTime createdAt;

    public StockMoveEvent(StockMove stockMove, ZonedDateTime createdAt) {
        this.stockMove = stockMove;
        this.createdAt = createdAt;
    }

    public StockMove getStockMove() {
        return stockMove;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
