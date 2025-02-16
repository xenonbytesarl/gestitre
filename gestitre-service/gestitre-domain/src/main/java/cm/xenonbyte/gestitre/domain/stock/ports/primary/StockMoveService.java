package cm.xenonbyte.gestitre.domain.stock.ports.primary;

import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveCreatedEvent;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMoveService {

    @Nonnull StockMoveCreatedEvent createStockMove(@Nonnull StockMove stockMove);
}
