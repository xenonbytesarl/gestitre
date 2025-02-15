package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 14/02/2025
 */
public final class CompanyCodeConflictException extends BaseDomainConflictException {
    public static final String COMPANY_CODE_CONFLICT = "CompanyCodeConflictException.1";
    public CompanyCodeConflictException(Object[] args) {
        super(COMPANY_CODE_CONFLICT, args);
    }
}
