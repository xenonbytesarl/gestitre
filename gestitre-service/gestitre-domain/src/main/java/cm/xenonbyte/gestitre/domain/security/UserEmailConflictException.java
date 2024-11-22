package cm.xenonbyte.gestitre.domain.security;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class UserEmailConflictException extends BaseDomainConflictException {
    public static final String USER_EMAIL_CONFLICT = "UserEmailConflictException.1";

    public UserEmailConflictException(Object[] args) {
        super(USER_EMAIL_CONFLICT, args);
    }
}
