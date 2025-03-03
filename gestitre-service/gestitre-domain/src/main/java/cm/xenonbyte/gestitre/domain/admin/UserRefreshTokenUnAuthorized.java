package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 27/02/2025
 */
public class UserRefreshTokenUnAuthorized extends BaseDomainUnAuthorizedException {

    public static final String USER_REFRESH_TOKEN_UNAUTHORIZED = "UserRefreshTokenUnAuthorized.1";

    public UserRefreshTokenUnAuthorized() {
        super(USER_REFRESH_TOKEN_UNAUTHORIZED);
    }
}
