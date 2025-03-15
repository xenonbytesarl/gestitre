package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public final class MailServerDefaultServerNotFoundException extends BaseDomainNotFoundException {

    private static final String MAIL_SERVER_DEFAULT_NOT_FOUND = "MailServerDefaultServerNotFoundException.1";

    public MailServerDefaultServerNotFoundException() {
        super(MAIL_SERVER_DEFAULT_NOT_FOUND);
    }
}
