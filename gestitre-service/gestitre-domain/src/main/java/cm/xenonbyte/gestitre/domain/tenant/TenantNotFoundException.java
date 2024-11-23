package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class TenantNotFoundException extends BaseDomainNotFoundException {
    public static final String TENANT_NOT_FOUND = "TenantNotFoundException.1";

    public TenantNotFoundException(Object[] args) {
        super(TENANT_NOT_FOUND, args);
    }
}
