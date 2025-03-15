package cm.xenonbyte.gestitre.domain.notification.event;

import cm.xenonbyte.gestitre.domain.notification.Mail;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public class MailEvent {
    private final Mail mail;
    private final ZonedDateTime createdAt;

    public MailEvent(Mail mail, ZonedDateTime createdAt) {
        this.mail = mail;
        this.createdAt = createdAt;
    }

    public Mail getMail() {
        return mail;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MailEvent mailEvent = (MailEvent) o;
        return Objects.equals(mail, mailEvent.mail) && Objects.equals(createdAt, mailEvent.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail, createdAt);
    }
}
