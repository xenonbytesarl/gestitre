package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 06/11/2024
 */
public final class CompanyIsinCodeConflictException extends BaseDomainConflictException {
    public static final String COMPANY_ISIN_CODE_CONFLICT = "CompanyIsinCodeConflict.1";

    public CompanyIsinCodeConflictException(Object[] args) {
        super(COMPANY_ISIN_CODE_CONFLICT, args);
    }
}
