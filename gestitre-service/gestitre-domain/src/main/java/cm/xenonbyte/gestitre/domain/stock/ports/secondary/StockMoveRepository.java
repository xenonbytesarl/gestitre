package cm.xenonbyte.gestitre.domain.stock.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMoveRepository {
    @Nonnull StockMove create(@Nonnull StockMove stockMove);

    @Nonnull PageInfo<StockMove> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);

    Optional<StockMove> findById(@Nonnull StockMoveId stockMoveId);

    @Nonnull StockMove update(@Nonnull StockMoveId stockMoveId, @Nonnull StockMove newStockMove);
}
