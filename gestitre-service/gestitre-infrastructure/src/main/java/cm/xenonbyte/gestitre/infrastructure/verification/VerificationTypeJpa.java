package cm.xenonbyte.gestitre.infrastructure.verification;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public enum VerificationTypeJpa {
    ACCOUNT("activate-account"), PASSWORD("reset-password"), MFA("mfa"), MAIL_SERVER("mail-server");

    private final String type;

    VerificationTypeJpa(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
