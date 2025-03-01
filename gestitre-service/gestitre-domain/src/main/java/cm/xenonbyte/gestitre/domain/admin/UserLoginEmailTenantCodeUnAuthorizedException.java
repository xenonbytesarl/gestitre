package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 02/03/2025
 */
public final class UserLoginEmailTenantCodeUnAuthorizedException extends BaseDomainNotFoundException {
    public static final String USER_LOGIN_TENANT_CODE_NOT_FOUND = "UserLoginEmailTenantCodeUnAuthorizedException.1";

    public UserLoginEmailTenantCodeUnAuthorizedException() {
        super(USER_LOGIN_TENANT_CODE_NOT_FOUND);
    }
}
