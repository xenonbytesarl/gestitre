package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 06/11/2024
 */
public final class CompanyRegistrationNumberConflictException extends BaseDomainConflictException {
    public static final String COMPANY_REGISTRATION_NUMBER_CONFLICT = "CompanyRegistrationNumberConflictException.1";

    public CompanyRegistrationNumberConflictException(Object[] args) {
        super(COMPANY_REGISTRATION_NUMBER_CONFLICT, args);
    }
}
