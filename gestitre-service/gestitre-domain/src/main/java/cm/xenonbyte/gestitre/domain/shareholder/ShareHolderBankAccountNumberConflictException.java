package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public final class ShareHolderBankAccountNumberConflictException extends BaseDomainConflictException {
    private static final String SHARE_HOLDER_BANK_ACCOUNT_NUMBER_CONFLICT = "ShareHolderBankAccountNumberConflictException.1";

    public ShareHolderBankAccountNumberConflictException(Object[] args) {
        super(SHARE_HOLDER_BANK_ACCOUNT_NUMBER_CONFLICT, args);
    }
}
