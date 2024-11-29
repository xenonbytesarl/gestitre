package cm.xenonbyte.gestitre.domain.admin.verification.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.verification.Verification;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCreatedEvent;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationService {
    @Nonnull
    VerificationCreatedEvent createVerification(@Nonnull Verification verification);
}
