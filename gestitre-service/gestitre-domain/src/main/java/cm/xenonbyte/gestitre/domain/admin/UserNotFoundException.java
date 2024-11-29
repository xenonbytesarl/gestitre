package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class UserNotFoundException extends BaseDomainNotFoundException {
    public static final String USER_ID_NOT_FOUND = "UserNotFoundException.1";

    public UserNotFoundException(Object[] args) {
        super(USER_ID_NOT_FOUND, args);
    }
}
