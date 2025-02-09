package cm.xenonbyte.gestitre.domain.admin.vo;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainBadException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/02/2025
 */
public final class TimezoneNameBadException extends BaseDomainBadException {
    public static final String TIMEZONE_NAME_BAD = "TimezoneNameBadException.1";

    public TimezoneNameBadException(Object[] args) {
        super(TIMEZONE_NAME_BAD, args);
    }
}
