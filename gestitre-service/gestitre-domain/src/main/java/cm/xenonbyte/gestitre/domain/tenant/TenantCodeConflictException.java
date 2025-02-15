package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 14/02/2025
 */
public final class TenantCodeConflictException extends BaseDomainConflictException {
    public static final String TENANT_CODE_CONFLICT = "TenantCodeConflictException.1";

    public TenantCodeConflictException(Object[] args) {
        super(TENANT_CODE_CONFLICT, args);
    }
}
