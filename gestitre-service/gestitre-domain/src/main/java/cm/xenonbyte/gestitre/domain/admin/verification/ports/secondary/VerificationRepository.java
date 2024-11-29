package cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary;

import cm.xenonbyte.gestitre.domain.admin.verification.Verification;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Code;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationId;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.admin.vo.UserId;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationRepository {
    @Nonnull Verification create(@Nonnull Verification verification);

    Optional<Verification> findByUserIdAndStatus(@Nonnull UserId userId, @Nonnull VerificationStatus stat);

    @Nonnull Verification update(@Nonnull VerificationId verificationId, @Nonnull Verification newVerification);

    Optional<Verification> findByCodeAndEmail(@Nonnull Code code, @Nonnull Email email);
}
