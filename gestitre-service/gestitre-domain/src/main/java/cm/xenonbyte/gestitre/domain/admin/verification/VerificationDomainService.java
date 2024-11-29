package cm.xenonbyte.gestitre.domain.admin.verification;

import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.message.publisher.VerificationMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.VerificationRepository;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import static cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationEventType.VERIFICATION_CANCELED;
import static cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationEventType.VERIFICATION_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@DomainService
public final class VerificationDomainService implements VerificationService {
    private final VerificationProvider verificationProvider;
    private final VerificationRepository verificationRepository;
    private final VerificationMessagePublisher verificationMessagePublish;

    public VerificationDomainService(
            @Nonnull VerificationProvider verificationProvider,
            @Nonnull VerificationRepository verificationRepository,
            @Nonnull VerificationMessagePublisher verificationMessagePublish
    ) {
        this.verificationProvider = Objects.requireNonNull(verificationProvider);
        this.verificationRepository = Objects.requireNonNull(verificationRepository);
        this.verificationMessagePublish = Objects.requireNonNull(verificationMessagePublish);
    }

    @Nonnull
    @Override
    public VerificationCreatedEvent createVerification(@Nonnull Verification verification) {
        verification.validateMandatoryFields();
        cancelExistingVerification(verification);
        initializeVerification(verification);
        verification.initializeDefaults();
        verificationRepository.create(verification);
        VerificationCreatedEvent verificationCreatedEvent = new VerificationCreatedEvent(verification, ZonedDateTime.now());
        verificationMessagePublish.publish(verificationCreatedEvent, VERIFICATION_CREATED);
        return verificationCreatedEvent;
    }

    private void initializeVerification(Verification verification) {
        if (Objects.requireNonNull(verification.getType()) == VerificationType.PASSWORD) {
            verification.initializeDefaultsWithUrl(verificationProvider.generateUrl(verification.getUrl(), verification.getType()));
        } else if (verification.getType() == VerificationType.ACCOUNT || verification.getType() == VerificationType.MFA) {
            verification.initializeDefaultsWithCode(verificationProvider.generateCode());
        }
    }

    private void cancelExistingVerification(Verification verification) {
        Optional<Verification> optionalVerification =
                verificationRepository.findByUserIdAndStatus(verification.getUserId(), VerificationStatus.NOT_VERIFIED);
        if (optionalVerification.isPresent()) {
            Verification oldVerification = optionalVerification.get();
            oldVerification.cancel();
            verificationRepository.update(oldVerification.getId(), oldVerification);
            VerificationCanceledEvent verificationCanceledEvent =
                    new VerificationCanceledEvent(oldVerification, ZonedDateTime.now());
            verificationMessagePublish.publish(verificationCanceledEvent, VERIFICATION_CANCELED);
        }
    }
}
