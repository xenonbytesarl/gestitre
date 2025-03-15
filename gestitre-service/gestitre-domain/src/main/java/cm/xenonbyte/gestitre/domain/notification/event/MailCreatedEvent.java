package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.notification.Mail;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
@DomainEvent
public final class MailCreatedEvent extends MailEvent{

    public MailCreatedEvent(Mail mail, ZonedDateTime createdAt) {
        super(mail, createdAt);
    }
}
