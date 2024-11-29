package cm.xenonbyte.gestitre.domain.common.verification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
public final class VerificationCodeNotException extends BaseDomainNotFoundException {
    public static final String VERIFICATION_SERVER_CODE_NOT_FOUND = "VerificationCodeNotException.1";

    public VerificationCodeNotException(Object[] args) {
        super(VERIFICATION_SERVER_CODE_NOT_FOUND, args);
    }
}
