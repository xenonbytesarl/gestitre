package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public final class CompanyNameNotFoundException extends BaseDomainNotFoundException {
    private static final String COMPANY_NAME_NOT_FOUND = "CompanyNameNotFoundException.1";

    public CompanyNameNotFoundException(Object[] args) {
        super(COMPANY_NAME_NOT_FOUND, args);
    }
}
