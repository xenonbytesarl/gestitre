package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 23/12/2024
 */
public final class UserEmailNotFoundException extends BaseDomainNotFoundException {
    public static final String USER_EMAIL_NOT_FOUND = "UserEmailNotFoundException.1";

    public UserEmailNotFoundException(Object[] args) {
        super(USER_EMAIL_NOT_FOUND, args);
    }
}
