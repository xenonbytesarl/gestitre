package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class UserEmailUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String USER_EMAIL_UN_AUTHORIZED = "UserEmailUnAuthorizedException.1";

    public UserEmailUnAuthorizedException() {
        super(USER_EMAIL_UN_AUTHORIZED);
    }
}
