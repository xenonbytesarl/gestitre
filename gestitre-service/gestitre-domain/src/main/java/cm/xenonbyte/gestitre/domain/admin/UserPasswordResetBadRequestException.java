package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;

/**
 * @author bamk
 * @version 1.0
 * @since 23/12/2024
 */
public final class UserPasswordResetBadRequestException extends BaseDomainBadException {
    private static final String USER_PASSWORD_RESET_BAD_REQUEST = "UserPasswordResetBadRequestException.1";

    public UserPasswordResetBadRequestException() {
        super(USER_PASSWORD_RESET_BAD_REQUEST);
    }
}
