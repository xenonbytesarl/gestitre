package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/12/2024
 */
public final class MailServerNameConflictException extends BaseDomainConflictException {
    public static final String MAIL_SERVER_NAME_CONFLICT = "MailServerNameConflictException.1";

    public MailServerNameConflictException(Object[] args) {
        super(MAIL_SERVER_NAME_CONFLICT, args);
    }
}
