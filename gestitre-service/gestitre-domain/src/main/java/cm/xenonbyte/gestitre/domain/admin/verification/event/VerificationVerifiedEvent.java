package cm.xenonbyte.gestitre.domain.admin.verification.event;

import cm.xenonbyte.gestitre.domain.admin.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@DomainEvent
public final class VerificationVerifiedEvent extends VerificationEvent {
    public VerificationVerifiedEvent(Verification verification, ZonedDateTime createdAt) {
        super(verification, createdAt);
    }
}
