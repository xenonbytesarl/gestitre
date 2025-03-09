package cm.xenonbyte.gestitre.domain.stock;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/03/2025
 */
public final class StockMoveUpdateStateBadRequestException extends BaseDomainBadException {
    private static final String STOCK_MOVE_UPDATE_STATE_BAD = "StockMoveUpdateStateBadRequestException.1";

    public StockMoveUpdateStateBadRequestException() {
        super(STOCK_MOVE_UPDATE_STATE_BAD);
    }
}
