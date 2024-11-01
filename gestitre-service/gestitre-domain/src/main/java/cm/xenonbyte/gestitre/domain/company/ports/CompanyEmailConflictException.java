package cm.xenonbyte.gestitre.domain.company.ports;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CompanyEmailConflictException extends BaseDomainConflictException {
    public static final String COMPANY_EMAIL_CONFLICT = "CompanyEmailConflictException.1";

    public CompanyEmailConflictException(Object[] args) {
        super(COMPANY_EMAIL_CONFLICT, args);
    }
}
