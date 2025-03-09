package cm.xenonbyte.gestitre.domain.stock.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveCreatedEvent;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveUpdateEvent;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMoveService {

    @Nonnull StockMoveCreatedEvent createStockMove(@Nonnull StockMove stockMove);

    @Nonnull PageInfo<StockMove> searchStockMoves(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);

    @Nonnull StockMove findStockMoveById(@Nonnull StockMoveId stockMoveId);

    @Nonnull StockMoveUpdateEvent updateStockMove(@Nonnull StockMoveId stockMoveId, @Nonnull StockMove newStockMove);
}
