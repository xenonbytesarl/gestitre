package cm.xenonbyte.gestitre.domain.common.exception;

/**
 * @author bamk
 * @version 1.0
 * @since 30/08/2024
 */
public final class PageInitializationBadException extends BaseDomainBadException {
    public static final String PAGE_INVALID_SUB_LIST_INDEX = "PageInitializationBadException.1";
    public static final String PAGE_INVALID_CURRENT_PAGE = "PageInitializationBadException.2";

    public PageInitializationBadException() {
        super(PAGE_INVALID_SUB_LIST_INDEX);
    }

    public PageInitializationBadException(Object[] args) {
        super(PAGE_INVALID_CURRENT_PAGE, args);
    }
}
