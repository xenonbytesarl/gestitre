package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserLoginPasswordUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_LOGIN_PASSWORD_UN_AUTHORIZED = "UserLoginPasswordUnAuthorizedException.1";

    public UserLoginPasswordUnAuthorizedException() {
        super(USER_LOGIN_PASSWORD_UN_AUTHORIZED);
    }
}
