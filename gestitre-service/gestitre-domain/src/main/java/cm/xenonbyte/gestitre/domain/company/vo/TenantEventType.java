package cm.xenonbyte.gestitre.domain.company.vo;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public enum TenantEventType {
    TENANT_CREATED("TENANT_CREATED");

    private final String value;

    TenantEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
