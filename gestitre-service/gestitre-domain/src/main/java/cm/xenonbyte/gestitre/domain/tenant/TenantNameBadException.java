package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public final class TenantNameBadException extends BaseDomainBadException {
    public static final String TENANT_NAME_BAD = "TenantNameBadException.1";

    public TenantNameBadException() {
        super(TENANT_NAME_BAD);
    }
}
