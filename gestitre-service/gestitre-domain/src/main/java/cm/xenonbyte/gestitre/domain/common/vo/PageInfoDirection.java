package cm.xenonbyte.gestitre.domain.common.vo;

/**
 * @author bamk
 * @version 1.0
 * @since 30/08/2024
 */
public enum PageInfoDirection {
    ASC("asc"), DESC("desc");

    private final String value;

    PageInfoDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
