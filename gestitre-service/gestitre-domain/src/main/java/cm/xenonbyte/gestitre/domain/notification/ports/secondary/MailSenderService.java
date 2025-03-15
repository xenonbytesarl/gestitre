package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.notification.Mail;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
public interface MailSenderService {
    void send(@Nonnull Mail mail, @Nonnull MailTemplate mailTemplate, @Nonnull MailServer mailServer);
}
