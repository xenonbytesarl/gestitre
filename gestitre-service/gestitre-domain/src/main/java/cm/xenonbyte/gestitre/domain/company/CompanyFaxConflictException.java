package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 18/01/2025
 */
public final class CompanyFaxConflictException extends BaseDomainConflictException {
    public static final String COMPANY_FAX_CONFLICT = "CompanyFaxConflictException.1";

    public CompanyFaxConflictException(Object[] args) {
        super(COMPANY_FAX_CONFLICT, args);
    }
}
