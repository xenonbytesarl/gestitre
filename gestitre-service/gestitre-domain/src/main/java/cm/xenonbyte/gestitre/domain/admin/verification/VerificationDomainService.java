package cm.xenonbyte.gestitre.domain.admin.verification;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.UserLoginEmailUnAuthorizedException;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationVerifiedEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.VerificationRepository;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.message.publisher.VerificationMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Code;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.admin.verification.VerificationCodeUnAuthorizedException.VERIFICATION_CODE_ALREADY_VERIFIED;
import static cm.xenonbyte.gestitre.domain.admin.verification.VerificationCodeUnAuthorizedException.VERIFICATION_CODE_EXPIRED;
import static cm.xenonbyte.gestitre.domain.admin.verification.VerificationCodeUnAuthorizedException.VERIFICATION_CODE_INVALID;
import static cm.xenonbyte.gestitre.domain.admin.verification.VerificationCodeUnAuthorizedException.VERIFICATION_CODE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationEventType.VERIFICATION_CANCELED;
import static cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationEventType.VERIFICATION_CREATED;
import static cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationEventType.VERIFICATION_VERIFIED;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@DomainService
public final class VerificationDomainService implements VerificationService {

    private static final Logger LOGGER = Logger.getLogger(VerificationDomainService.class.getName());

    private final VerificationProvider verificationProvider;
    private final VerificationRepository verificationRepository;
    private final VerificationMessagePublisher verificationMessagePublish;
    private final UserRepository userRepository;

    public VerificationDomainService(
            @Nonnull VerificationProvider verificationProvider,
            @Nonnull VerificationRepository verificationRepository,
            @Nonnull VerificationMessagePublisher verificationMessagePublish,
            @Nonnull UserRepository userRepository
    ) {
        this.verificationProvider = Objects.requireNonNull(verificationProvider);
        this.verificationRepository = Objects.requireNonNull(verificationRepository);
        this.verificationMessagePublish = Objects.requireNonNull(verificationMessagePublish);
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Nonnull
    @Override
    public VerificationCreatedEvent createVerification(@Nonnull Verification verification) {
        verification.validateMandatoryFields();
        cancelExistingVerification(verification);
        initializeVerification(verification);
        verification.initializeDefaults();
        verificationRepository.create(verification);
        LOGGER.info("Verification created with id  " + verification.getId().getValue());
        VerificationCreatedEvent verificationCreatedEvent = new VerificationCreatedEvent(verification, ZonedDateTime.now());
        verificationMessagePublish.publish(verificationCreatedEvent, VERIFICATION_CREATED);
        return verificationCreatedEvent;
    }

    @Nonnull
    @Override
    public User verifyCode(@Nonnull Code code, @Nonnull Email email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserLoginEmailUnAuthorizedException::new);
        Optional<Verification> optionalVerification = verificationRepository.findByCodeAndEmail(code, email);
        Verification oldVerification = validateVerificationCode(optionalVerification, code);
        oldVerification.verify();
        LOGGER.info("Verification verified");
        verificationRepository.update(oldVerification.getId(), oldVerification);
        VerificationVerifiedEvent verificationVerifiedEvent =
                new VerificationVerifiedEvent(oldVerification, ZonedDateTime.now());

        verificationMessagePublish.publish(verificationVerifiedEvent, VERIFICATION_VERIFIED);

        return user;
    }

    @Nonnull
    @Override
    public VerificationCreatedEvent resendVerification(@Nonnull Email email, @Nonnull VerificationType type, @Nonnull Long duration) {
        User user = userRepository.findByEmail(email).orElseThrow(UserLoginEmailUnAuthorizedException::new);
        return createVerification(
                Verification.builder()
                    .email(email)
                    .type(type)
                    .duration(Duration.of(duration))
                    .userId(user.getId())
                    .build()
        );
    }

    private Verification validateVerificationCode(Optional<Verification> optionalVerification, Code code) {
        if(optionalVerification.isEmpty()) {
            throw new VerificationCodeUnAuthorizedException(VERIFICATION_CODE_NOT_FOUND);
        }

        Verification verification = optionalVerification.get();

        if(verification.getStatus() != null && verification.getStatus().equals(VerificationStatus.VERIFIED)) {
            throw new VerificationCodeUnAuthorizedException(VERIFICATION_CODE_ALREADY_VERIFIED);
        }

        if(verification.getCode() != null && !verification.getCode().text().value().isEmpty() && !verification.getCode().equals(code)) {
            throw new VerificationCodeUnAuthorizedException(VERIFICATION_CODE_INVALID);
        }

        if(verification.getExpiredAt() != null && verification.getExpiredAt().isBefore(ZonedDateTime.now())) {
            throw new VerificationCodeUnAuthorizedException(VERIFICATION_CODE_EXPIRED);
        }
        return verification;
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
            LOGGER.info("Verification cancelled");
            VerificationCanceledEvent verificationCanceledEvent =
                    new VerificationCanceledEvent(oldVerification, ZonedDateTime.now());
            verificationMessagePublish.publish(verificationCanceledEvent, VERIFICATION_CANCELED);
        }
    }
}
