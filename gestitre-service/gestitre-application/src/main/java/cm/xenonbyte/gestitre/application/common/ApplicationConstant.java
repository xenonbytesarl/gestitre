package cm.xenonbyte.gestitre.application.common;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
public final class ApplicationConstant {

    private ApplicationConstant() {}

    public static final String ROOT_API_PATH = "/api/v1/gestitre";
    public static final String CERTIFICATE_TEMPLATE_API_PATH = ROOT_API_PATH + "/certificate-templates";
    public static final String COMPANY_API_PATH = ROOT_API_PATH + "/companies";
    public static final String USER_API_PATH = ROOT_API_PATH + "/users";
    public static final String MAIL_SERVER_API_PATH = ROOT_API_PATH + "/mail-servers";
    public static final String SHARE_HOLDER_API_PATH = ROOT_API_PATH + "/shareholders";
    public static final String STOCK_MOVE_API_PATH = ROOT_API_PATH + "/stock-moves";
    public static final String CONTENT = "content";

    public static final String NOT_EMPTY = "ApplicationConstant.NotEmpty";
    public static final String NOT_BLANK = "ApplicationConstant.NotBlank";
    public static final String MAX_SIZE = "ApplicationConstant.MaxSize";
    public static final String MIN_SIZE = "ApplicationConstant.MinSize";
    public static final String MIN_MAX_SIZE = "ApplicationConstant.MinMaxSize";
    public static final String EMAIL = "ApplicationConstant.Email";
    public static final String NOT_NULL = "ApplicationConstant.NotNull";
    public static final String POSITIVE_OR_ZERO = "ApplicationConstant.PositiveOrZero";

    public static final String FR_LANGUAGE = "fr";
}
