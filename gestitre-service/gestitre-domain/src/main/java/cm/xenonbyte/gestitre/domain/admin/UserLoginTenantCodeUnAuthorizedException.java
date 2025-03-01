package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 02/03/2025
 */
public final class UserLoginTenantCodeUnAuthorizedException extends BaseDomainUnAuthorizedException {
    private static final String USER_LOGIN_TENANT_CODE_UN_AUTHORIZED = "UserLoginTenantCodeUnAuthorizedException.1";

    public UserLoginTenantCodeUnAuthorizedException() {
        super(USER_LOGIN_TENANT_CODE_UN_AUTHORIZED);
    }
}
