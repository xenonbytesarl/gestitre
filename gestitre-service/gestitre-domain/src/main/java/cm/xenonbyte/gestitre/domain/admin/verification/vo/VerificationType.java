package cm.xenonbyte.gestitre.domain.admin.verification.vo;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public enum VerificationType {
    ACCOUNT("activate-account"), PASSWORD("reset-password"), MFA("mfa");

    private final String type;

    VerificationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
