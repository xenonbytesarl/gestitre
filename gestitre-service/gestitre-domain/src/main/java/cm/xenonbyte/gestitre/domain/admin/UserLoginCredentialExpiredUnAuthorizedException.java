package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserLoginCredentialExpiredUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_LOGIN_CREDENTIAL_EXPIRED_UN_AUTHORIZED = "UserLoginCredentialExpiredUnAuthorizedException.1";

    public UserLoginCredentialExpiredUnAuthorizedException() {
        super(USER_LOGIN_CREDENTIAL_EXPIRED_UN_AUTHORIZED);
    }
}
