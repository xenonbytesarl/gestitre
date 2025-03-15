package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public final class MailTemplateTypeConflictException extends BaseDomainConflictException {
    private static final String MAIL_TEMPLATE_TYPE_CONFLICT = "MailTemplateTypeConflictException.1";

    public MailTemplateTypeConflictException(Object[] args) {
        super(MAIL_TEMPLATE_TYPE_CONFLICT, args);
    }
}
