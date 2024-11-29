package cm.xenonbyte.gestitre.domain.common.verification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class VerificationUrlUnAuthorizedException extends BaseDomainNotFoundException {

    public static final String VERIFICATION_ACCOUNT_URL_NOT_FOUND = "VerificationUrlUnAuthorizedException.1";
    public static final String VERIFICATION_ACCOUNT_URL_ALREADY_VERIFIED = "VerificationUrlUnAuthorizedException.2";
    public static final String VERIFICATION_ACCOUNT_URL_INVALID = "VerificationUrlUnAuthorizedException.3";
    public static final String VERIFICATION_ACCOUNT_URL_EXPIRED = "VerificationUrlUnAuthorizedException.4";
    public static final String VERIFICATION_PASSWORD_URL_NOT_FOUND = "VerificationUrlUnAuthorizedException.5";
    public static final String VERIFICATION_PASSWORD_URL_ALREADY_VERIFIED = "VerificationUrlUnAuthorizedException.6";
    public static final String VERIFICATION_PASSWORD_URL_INVALID = "VerificationUrlUnAuthorizedException.7";
    public static final String VERIFICATION_PASSWORD_URL_EXPIRED = "VerificationUrlUnAuthorizedException.8";

    public VerificationUrlUnAuthorizedException(String message) {
        super(message);
    }
}
