package cm.xenonbyte.gestitre.domain.common.verification.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public interface VerificationMessageListener {

    void handle(UserCreatedEvent event);
    void handle(MailServerCreatedEvent event);
}
