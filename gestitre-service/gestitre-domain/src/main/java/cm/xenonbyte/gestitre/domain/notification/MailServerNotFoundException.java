package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
public final class MailServerNotFoundException extends BaseDomainNotFoundException {
    private static final String MAIL_SERVER_NOT_FOUND = "MailServerNotFoundException.1";

    public MailServerNotFoundException(Object[] args) {
        super(MAIL_SERVER_NOT_FOUND, args);
    }
}
