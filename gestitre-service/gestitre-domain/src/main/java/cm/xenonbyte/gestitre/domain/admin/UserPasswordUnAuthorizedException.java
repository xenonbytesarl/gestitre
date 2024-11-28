package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserPasswordUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_PASSWORD_UN_AUTHORIZED = "UserPasswordUnAuthorizedException.1";

    public UserPasswordUnAuthorizedException() {
        super(USER_PASSWORD_UN_AUTHORIZED);
    }
}
