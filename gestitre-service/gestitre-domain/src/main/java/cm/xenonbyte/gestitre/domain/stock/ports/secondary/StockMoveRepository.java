package cm.xenonbyte.gestitre.domain.stock.ports.secondary;

import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMoveRepository {
    @Nonnull StockMove create(@Nonnull StockMove stockMove);
}
