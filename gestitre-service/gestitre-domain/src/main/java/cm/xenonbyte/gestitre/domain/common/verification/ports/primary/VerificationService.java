package cm.xenonbyte.gestitre.domain.common.verification.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationService {
    @Nonnull VerificationCreatedEvent createVerification(@Nonnull Verification verification);
    @Nonnull User verifyCode(@Nonnull Code code, @Nonnull Email email);
    @Nonnull User verifyCode(@Nonnull Code code, VerificationType account);
    @Nonnull MailServer verifyCode(@Nonnull Code code);
    @Nonnull VerificationCreatedEvent resendVerification(@Nonnull Email email, @Nonnull VerificationType type, @Nonnull Long duration);

}
