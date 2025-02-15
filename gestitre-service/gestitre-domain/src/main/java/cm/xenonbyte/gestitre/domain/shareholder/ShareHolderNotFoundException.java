package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 15/02/2025
 */
public final class ShareHolderNotFoundException extends BaseDomainNotFoundException {
    public static final String SHARE_HOLDER_NOT_FOUND = "ShareHolderNotFoundException.1";

    public ShareHolderNotFoundException(Object[] args) {
        super(SHARE_HOLDER_NOT_FOUND, args);
    }
}
