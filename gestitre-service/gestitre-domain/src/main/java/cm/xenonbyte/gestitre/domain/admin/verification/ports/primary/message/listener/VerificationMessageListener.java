package cm.xenonbyte.gestitre.domain.admin.verification.ports.primary.message.listener;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public interface VerificationMessageListener {

    void handle(UserCreatedEvent event);
}
