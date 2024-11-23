package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public final class TenantNameConflictException extends BaseDomainConflictException {
    public static final String TENANT_NAME_CONFLICT = "TenantNameConflictException.1";

    public TenantNameConflictException(Object[] args) {
        super(TENANT_NAME_CONFLICT, args);
    }
}
