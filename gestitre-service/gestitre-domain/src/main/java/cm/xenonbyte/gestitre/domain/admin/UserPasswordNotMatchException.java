package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class UserPasswordNotMatchException extends BaseDomainBadException {
    private static final String USER_PASSWORD_NOT_MATCH = "UserPasswordNotMatchException.1";

    public UserPasswordNotMatchException() {
        super(USER_PASSWORD_NOT_MATCH);
    }
}
