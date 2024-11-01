package cm.xenonbyte.gestitre.domain.company.ports;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CompanyNameConflictException extends BaseDomainConflictException {
    public static final String COMPANY_NAME_CONFLICT = "CompanyNameConflictException.1";

    public CompanyNameConflictException(Object[] args) {
        super(COMPANY_NAME_CONFLICT, args);
    }
}
