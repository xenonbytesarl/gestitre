package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public final class ShareHolderPhoneConflictException extends BaseDomainConflictException {
    private static final String SHARE_HOLDER_PHONE_CONFLICT = "ShareHolderPhoneConflictException.1";

    public ShareHolderPhoneConflictException(Object[] args) {
        super(SHARE_HOLDER_PHONE_CONFLICT, args);
    }
}
