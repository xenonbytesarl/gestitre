package cm.xenonbyte.gestitre.domain.stock.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public final class StockMoveId extends BaseId<UUID> {
    public StockMoveId(@Nonnull UUID value) {
        super(value);
    }
}
