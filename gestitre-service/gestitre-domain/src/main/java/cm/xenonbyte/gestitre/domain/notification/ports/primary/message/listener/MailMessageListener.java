package cm.xenonbyte.gestitre.domain.notification.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
public interface MailMessageListener {
    void handle(VerificationCreatedEvent event);
}
