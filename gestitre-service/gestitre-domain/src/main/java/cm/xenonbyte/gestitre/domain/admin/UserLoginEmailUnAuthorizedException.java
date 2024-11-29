package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserLoginEmailUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_LOGIN_EMAIL_UN_AUTHORIZED = "UserLoginEmailUnAuthorizedException.1";

    public UserLoginEmailUnAuthorizedException() {
        super(USER_LOGIN_EMAIL_UN_AUTHORIZED);
    }
}
