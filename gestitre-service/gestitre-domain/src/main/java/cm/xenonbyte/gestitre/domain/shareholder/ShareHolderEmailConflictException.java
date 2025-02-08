package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public final class ShareHolderEmailConflictException extends BaseDomainConflictException {
    private static final String SHARE_HOLDER_EMAIL_CONFLICT = "ShareHolderEmailConflictException.1";

    public ShareHolderEmailConflictException(Object[] args) {
        super(SHARE_HOLDER_EMAIL_CONFLICT, args);
    }
}
