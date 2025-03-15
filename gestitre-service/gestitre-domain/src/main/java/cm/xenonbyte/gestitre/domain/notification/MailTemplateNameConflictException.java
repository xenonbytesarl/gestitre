package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public final class MailTemplateNameConflictException extends BaseDomainConflictException {
    private static final String MAIL_TEMPLATE_NAME_CONFLICT = "MailTemplateNameConflictException.1";

    public MailTemplateNameConflictException(Object[] args) {
        super(MAIL_TEMPLATE_NAME_CONFLICT, args);
    }
}
