package cm.xenonbyte.gestitre.domain.stock;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 08/03/2025
 */
public final class StockMoveNotFoundException extends BaseDomainNotFoundException {
    private static final String STOCK_MOVE_NOT_FOUND = "StockMoveNotFoundException.1";

    public StockMoveNotFoundException(Object[] args) {
        super(STOCK_MOVE_NOT_FOUND, args);
    }
}
