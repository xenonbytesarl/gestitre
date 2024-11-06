package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 06/11/2024
 */
public final class CompanyTaxNumberConflictException extends BaseDomainConflictException {
    public static final String COMPANY_TAX_NUMBER_CONFLICT = "CompanyTaxNumberConflict.1";

    public CompanyTaxNumberConflictException(Object[] args) {
        super(COMPANY_TAX_NUMBER_CONFLICT, args);
    }
}
