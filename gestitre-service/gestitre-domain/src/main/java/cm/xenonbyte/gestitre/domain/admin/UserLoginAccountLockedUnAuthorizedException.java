package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserLoginAccountLockedUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_LOGIN_ACCOUNT_LOCKED_UN_AUTHORIZED = "UserLoginAccountLockedUnAuthorizedException.1";

    public UserLoginAccountLockedUnAuthorizedException() {
        super(USER_LOGIN_ACCOUNT_LOCKED_UN_AUTHORIZED);
    }
}
