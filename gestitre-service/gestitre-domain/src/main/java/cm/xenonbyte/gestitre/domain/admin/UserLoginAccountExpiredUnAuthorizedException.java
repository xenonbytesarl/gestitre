package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserLoginAccountExpiredUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_LOGIN_ACCOUNT_EXPIRED_UN_AUTHORIZED = "UserLoginAccountExpiredUnAuthorizedException.1";

    public UserLoginAccountExpiredUnAuthorizedException() {
        super(USER_LOGIN_ACCOUNT_EXPIRED_UN_AUTHORIZED);
    }
}
