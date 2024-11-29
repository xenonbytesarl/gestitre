package cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.message.publisher;

import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationEvent;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationMessagePublisher {
    void publish(VerificationEvent event, VerificationEventType type);
}
