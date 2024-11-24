package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class RoleNotFoundException extends BaseDomainNotFoundException {
    public static final String ROLE_NOT_FOUND = "RoleNotFoundException.1";

    public RoleNotFoundException(Object[] args) {
        super(ROLE_NOT_FOUND, args);
    }
}
