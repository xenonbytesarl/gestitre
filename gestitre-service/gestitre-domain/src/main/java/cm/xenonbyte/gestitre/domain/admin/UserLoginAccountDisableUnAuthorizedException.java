package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserLoginAccountDisableUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_LOGIN_ACCOUNT_DISABLE_UN_AUTHORIZED = "UserLoginAccountDisableUnAuthorizedException.1";

    public UserLoginAccountDisableUnAuthorizedException() {
        super(USER_LOGIN_ACCOUNT_DISABLE_UN_AUTHORIZED);
    }
}
