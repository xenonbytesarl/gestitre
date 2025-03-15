package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainTechnicalException;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
public final class SendMailTechnicalException extends BaseDomainTechnicalException {
    public static final String SEND_MAIL_TECHNICAL = "SendMailTechnicalException.1";

    public SendMailTechnicalException(Object[] args) {
        super(SEND_MAIL_TECHNICAL, args);
    }
}
