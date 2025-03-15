package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public final class MailTemplateTypeNotFoundException extends BaseDomainNotFoundException {
    private static final String MAIL_TEMPLATE_TYPE_NOT_FOUND = "MailTemplateTypeNotFoundException.1";

    public MailTemplateTypeNotFoundException(Object[] args) {
        super(MAIL_TEMPLATE_TYPE_NOT_FOUND, args);
    }
}
