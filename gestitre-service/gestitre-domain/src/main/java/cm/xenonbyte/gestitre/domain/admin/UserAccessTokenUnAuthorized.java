package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 02/03/2025
 */
public final class UserAccessTokenUnAuthorized extends BaseDomainUnAuthorizedException {

    public static final String USER_ACCESS_TOKEN_UNAUTHORIZED = "UserAccessTokenUnAuthorized.1";

    public UserAccessTokenUnAuthorized() {
        super(USER_ACCESS_TOKEN_UNAUTHORIZED);
    }
}