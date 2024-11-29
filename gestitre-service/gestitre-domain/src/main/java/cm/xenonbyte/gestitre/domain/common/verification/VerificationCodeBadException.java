package cm.xenonbyte.gestitre.domain.common.verification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
public final class VerificationCodeBadException extends BaseDomainBadException {

    public static final String VERIFICATION_MAIL_SERVER_CODE_NOT_FOUND = "VerificationCodeBadException.1";
    public static final String VERIFICATION_MAIL_SERVER_CODE_ALREADY_VERIFIED = "VerificationCodeBadException.2";
    public static final String VERIFICATION_MAIL_SERVER_CODE_INVALID = "VerificationCodeBadException.3";
    public static final String VERIFICATION_MAIL_SERVER_CODE_EXPIRED = "VerificationCodeBadException.4";

    public VerificationCodeBadException(String message) {
        super(message);
    }
}
