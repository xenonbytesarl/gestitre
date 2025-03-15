package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public final class MailNotFoundException extends BaseDomainNotFoundException {

    private static final String MAIL_NOT_FOUND = "MailNotFoundException.1";

    public MailNotFoundException(Object[] args) {
        super(MAIL_NOT_FOUND, args);
    }
}
