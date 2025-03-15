package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public final class MailTemplateNotFoundException extends BaseDomainNotFoundException {
    private static final String MAIL_TEMPLATE_NOT_FOUND = "MailTemplateNotFoundException.1";

    public MailTemplateNotFoundException(Object[] args) {
        super(MAIL_TEMPLATE_NOT_FOUND, args);
    }
}
