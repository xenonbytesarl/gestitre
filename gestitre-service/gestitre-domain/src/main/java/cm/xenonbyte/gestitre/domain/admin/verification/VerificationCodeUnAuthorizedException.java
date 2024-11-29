package cm.xenonbyte.gestitre.domain.admin.verification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainUnAuthorizedException;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class VerificationCodeUnAuthorizedException extends BaseDomainUnAuthorizedException {
    public static final String VERIFICATION_CODE_NOT_FOUND = "VerificationCodeUnAuthorizedException.1";
    public static final String VERIFICATION_CODE_ALREADY_VERIFIED = "VerificationCodeUnAuthorizedException.2";
    public static final String VERIFICATION_CODE_INVALID = "VerificationCodeUnAuthorizedException.3";
    public static final String VERIFICATION_CODE_EXPIRED = "VerificationCodeUnAuthorizedException.4";
    public VerificationCodeUnAuthorizedException(String message) {
        super(message);
    }
}
