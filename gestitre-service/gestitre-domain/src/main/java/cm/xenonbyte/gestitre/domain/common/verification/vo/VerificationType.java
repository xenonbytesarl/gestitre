package cm.xenonbyte.gestitre.domain.common.verification.vo;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public enum VerificationType {
    ACCOUNT("activate-account"), PASSWORD("reset-password"), MFA("mfa"), MAIL_SERVER("mail-server");

    private final String type;

    VerificationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
