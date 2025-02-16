package cm.xenonbyte.gestitre.domain.stock.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public final class StockMoveLineId extends BaseId<UUID> {
    public StockMoveLineId(@Nonnull UUID value) {
        super(value);
    }
}
