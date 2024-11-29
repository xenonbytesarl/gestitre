package cm.xenonbyte.gestitre.domain.admin.verification.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.verification.Verification;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Code;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationService {
    @Nonnull VerificationCreatedEvent createVerification(@Nonnull Verification verification);
    @Nonnull User verifyCode(@Nonnull Code code, @Nonnull Email email);
    @Nonnull VerificationCreatedEvent resendVerification(@Nonnull Email email, @Nonnull VerificationType type, @Nonnull Long duration);
}
