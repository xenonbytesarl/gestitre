package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public final class ShareHolderAccountNumberConflictException extends BaseDomainConflictException {
    private static final String SHARE_HOLDER_ACCOUNT_NUMBER_CONFLICT = "ShareHolderAccountNumberConflictException.1";

    public ShareHolderAccountNumberConflictException(Object[] args) {
        super(SHARE_HOLDER_ACCOUNT_NUMBER_CONFLICT, args);
    }
}
