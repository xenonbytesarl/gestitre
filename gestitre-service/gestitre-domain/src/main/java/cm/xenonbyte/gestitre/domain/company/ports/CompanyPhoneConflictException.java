package cm.xenonbyte.gestitre.domain.company.ports;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CompanyPhoneConflictException extends BaseDomainConflictException {
    public static final String COMPANY_PHONE_CONFLICT = "CompanyPhoneConflictException.1";

    public CompanyPhoneConflictException(Object[] args) {
        super(COMPANY_PHONE_CONFLICT, args);
    }
}
