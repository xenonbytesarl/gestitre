package cm.xenonbyte.gestitre.infrastructure.verification;


import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationRepository;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationId;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerId;
import cm.xenonbyte.gestitre.infrastructure.admin.UserJpa;
import cm.xenonbyte.gestitre.infrastructure.notification.MailServerJpa;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@ApplicationScoped
public final class VerificationJpaRepositoryAdapter implements VerificationRepository {

    private final VerificationJpaRepository verificationJpaRepository;
    private final VerificationJpaMapper verificationJpaMapper;

    public VerificationJpaRepositoryAdapter(
            @Nonnull VerificationJpaRepository verificationJpaRepository,
            @Nonnull VerificationJpaMapper verificationJpaMapper
    ) {
        this.verificationJpaRepository = Objects.requireNonNull(verificationJpaRepository);
        this.verificationJpaMapper = Objects.requireNonNull(verificationJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public Verification create(@Nonnull Verification verification) {
        verificationJpaRepository.persist(verificationJpaMapper.toVerificationJpa(verification));
        return verificationJpaMapper.toVerification(
                verificationJpaRepository.findById(verification.getId().getValue())
        );
    }

    @Override
    public Optional<Verification> findByUserIdAndStatus(@Nonnull UserId userId, @Nonnull VerificationStatus status) {
        return verificationJpaRepository.findByUserAndStatus(UserJpa.builder().id(userId.getValue()).build(), VerificationStatusJpa.valueOf(status.name()))
                .map(verificationJpaMapper::toVerification);
    }

    @Nonnull
    @Override
    @Transactional
    public Verification update(@Nonnull VerificationId verificationId, @Nonnull Verification newVerification) {
        VerificationJpa oldVerificationJpa = verificationJpaRepository.findById(verificationId.getValue());
        VerificationJpa newVerificationJpa = verificationJpaMapper.toVerificationJpa(newVerification);
        verificationJpaMapper.copyNewToOldVerificationJpa(newVerificationJpa, oldVerificationJpa);
        return verificationJpaMapper.toVerification(oldVerificationJpa);
    }

    @Override
    public Optional<Verification> findByCodeAndEmail(@Nonnull Code code, @Nonnull Email email) {
        return verificationJpaRepository.findByCodeAndEmail(code.text().value(), email.text().value())
                .map(verificationJpaMapper::toVerification);
    }

    @Override
    public Optional<Verification> findByCode(Code code) {
        return verificationJpaRepository.findByCode(code.text().value())
                .map(verificationJpaMapper::toVerification);
    }

    @Override
    public Optional<Verification> findByMailServerIdAndStatus(@Nonnull MailServerId mailServerId, @Nonnull VerificationStatus status) {
        return verificationJpaRepository.findByMailServerAndStatus(MailServerJpa.builder().id(mailServerId.getValue()).build(), VerificationStatusJpa.valueOf(status.name()))
                .map(verificationJpaMapper::toVerification);
    }
}
