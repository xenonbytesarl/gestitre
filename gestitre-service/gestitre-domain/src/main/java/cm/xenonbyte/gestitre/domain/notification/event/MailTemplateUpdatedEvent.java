package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@DomainEvent
public final class MailTemplateUpdatedEvent extends MailTemplateEvent {
    public MailTemplateUpdatedEvent(MailTemplate mailTemplate, ZonedDateTime createdAt) {
        super(mailTemplate, createdAt);
    }
}
