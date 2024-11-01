package cm.xenonbyte.gestitre.domain.company.ports;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CompanyNotFoundException extends BaseDomainNotFoundException {
    public static final String COMPANY_NOT_FOUND = "CompanyNotFoundException.1";

    public CompanyNotFoundException(Object[] args) {
        super(COMPANY_NOT_FOUND, args);
    }
}
