package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/02/2025
 */
public final class CompanyTenantIdNotFoundException extends BaseDomainNotFoundException {
    public static final String COMPANY_TENANT_ID_NOT_FOUND = "CompanyTenantIdNotFoundException.1";

    public CompanyTenantIdNotFoundException(Object[] args) {
        super(COMPANY_TENANT_ID_NOT_FOUND,args);
    }
}
