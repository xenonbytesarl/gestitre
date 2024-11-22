package cm.xenonbyte.gestitre.domain.security;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public final class TenantNameNotFoundException extends BaseDomainNotFoundException {
    public static final String TENANT_NAME_NOT_FOUND = "TenantNameNotFoundException.1";

    public TenantNameNotFoundException(Object[] args) {
        super(TENANT_NAME_NOT_FOUND, args);
    }
}
