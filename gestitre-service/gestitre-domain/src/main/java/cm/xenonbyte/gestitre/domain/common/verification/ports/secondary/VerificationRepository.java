package cm.xenonbyte.gestitre.domain.common.verification.ports.secondary;


import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationId;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
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

    Optional<Verification> findByCode(Code code);

    Optional<Verification> findByMailServerIdAndStatus(@Nonnull MailServerId mailServerId, @Nonnull VerificationStatus verificationStatus);
}
