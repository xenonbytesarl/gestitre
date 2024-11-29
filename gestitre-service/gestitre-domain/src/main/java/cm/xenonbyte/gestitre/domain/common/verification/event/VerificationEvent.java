package cm.xenonbyte.gestitre.domain.common.verification.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.common.verification.Verification;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@DomainEvent
public class VerificationEvent implements BaseEvent<Verification> {
    private final Verification verification;
    private final ZonedDateTime createdAt;

    public VerificationEvent(Verification verification, ZonedDateTime createdAt) {
        this.verification = Objects.requireNonNull(verification);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    public Verification getVerification() {
        return verification;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
