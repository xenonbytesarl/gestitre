package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
public class MailTemplateEvent implements BaseEvent<MailTemplate> {
    private final MailTemplate mailTemplate;
    private final ZonedDateTime createdAt;

    public MailTemplateEvent(MailTemplate mailTemplate, ZonedDateTime createdAt) {
        this.mailTemplate = mailTemplate;
        this.createdAt = createdAt;
    }

    public MailTemplate getMailTemplate() {
        return mailTemplate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MailTemplateEvent that = (MailTemplateEvent) o;
        return Objects.equals(mailTemplate, that.mailTemplate) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailTemplate, createdAt);
    }
}
