package cm.xenonbyte.gestitre.domain.common.verification;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.UserLoginEmailUnAuthorizedException;
import cm.xenonbyte.gestitre.domain.admin.UserNotFoundException;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationVerifiedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationRepository;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.message.publisher.VerificationMessagePublisher;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.MailServerNotFoundException;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailServerRepository;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeBadException.VERIFICATION_MAIL_SERVER_CODE_ALREADY_VERIFIED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeBadException.VERIFICATION_MAIL_SERVER_CODE_EXPIRED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeBadException.VERIFICATION_MAIL_SERVER_CODE_INVALID;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeBadException.VERIFICATION_MAIL_SERVER_CODE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeUnAuthorizedException.VERIFICATION_MFA_CODE_ALREADY_VERIFIED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeUnAuthorizedException.VERIFICATION_MFA_CODE_EXPIRED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeUnAuthorizedException.VERIFICATION_MFA_CODE_INVALID;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationCodeUnAuthorizedException.VERIFICATION_MFA_CODE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_ACCOUNT_URL_ALREADY_VERIFIED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_ACCOUNT_URL_EXPIRED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_ACCOUNT_URL_INVALID;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_ACCOUNT_URL_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_PASSWORD_URL_ALREADY_VERIFIED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_PASSWORD_URL_EXPIRED;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_PASSWORD_URL_INVALID;
import static cm.xenonbyte.gestitre.domain.common.verification.VerificationUrlUnAuthorizedException.VERIFICATION_PASSWORD_URL_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationEventType.VERIFICATION_CANCELED;
import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationEventType.VERIFICATION_CREATED;
import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationEventType.VERIFICATION_VERIFIED;
import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType.MAIL_SERVER;
import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType.MFA;

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
    private final MailServerRepository mailServerRepository;

    public VerificationDomainService(
            @Nonnull VerificationProvider verificationProvider,
            @Nonnull VerificationRepository verificationRepository,
            @Nonnull VerificationMessagePublisher verificationMessagePublish,
            @Nonnull UserRepository userRepository,
            @Nonnull MailServerRepository mailServerRepository
    ) {
        this.verificationProvider = Objects.requireNonNull(verificationProvider);
        this.verificationRepository = Objects.requireNonNull(verificationRepository);
        this.verificationMessagePublish = Objects.requireNonNull(verificationMessagePublish);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.mailServerRepository = Objects.requireNonNull(mailServerRepository);
    }

    @Nonnull
    @Override
    public VerificationCreatedEvent createVerification(@Nonnull Verification verification) {
        verification.validateMandatoryFields();
        cancelExistingVerification(verification);
        initializeVerification(verification);
        verification.initializeDefaults();
        verification = verificationRepository.create(verification);
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
        Verification oldVerification = validateVerificationCode(optionalVerification, code, MFA);
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

    @Nonnull
    @Override
    public User verifyCode(@Nonnull Code code, @Nonnull VerificationType type) {
        Optional<Verification> optionalVerification = verificationRepository.findByCode(code);
        if (optionalVerification.isPresent()) {
            Verification oldVerification = validateVerificationUrl(optionalVerification, code, type);
            oldVerification.verify();
            LOGGER.info("Verification verified");
            verificationRepository.update(oldVerification.getId(), oldVerification);
            VerificationVerifiedEvent verificationVerifiedEvent =
                    new VerificationVerifiedEvent(oldVerification, ZonedDateTime.now());

            verificationMessagePublish.publish(verificationVerifiedEvent, VERIFICATION_VERIFIED);
            return userRepository.findById(oldVerification.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(new String[] {oldVerification.getUserId().getValue().toString()}));
        }
        throw new VerificationCodeUnAuthorizedException(VERIFICATION_MFA_CODE_NOT_FOUND);
    }

    @Nonnull
    @Override
    public MailServer verifyCode(@Nonnull Code code) {
        Optional<Verification> optionalVerification = verificationRepository.findByCode(code);
        if (optionalVerification.isPresent()) {
            Verification oldVerification = validateVerificationCode(optionalVerification, code, MAIL_SERVER);
            oldVerification.verify();
            LOGGER.info("Verification verified");
            verificationRepository.update(oldVerification.getId(), oldVerification);
            VerificationVerifiedEvent verificationVerifiedEvent =
                    new VerificationVerifiedEvent(oldVerification, ZonedDateTime.now());

            verificationMessagePublish.publish(verificationVerifiedEvent, VERIFICATION_VERIFIED);
            return mailServerRepository.findById(oldVerification.getMailServerId())
                    .orElseThrow(() -> new MailServerNotFoundException(new String[] {oldVerification.getMailServerId().getValue().toString()}));
        }
        throw new VerificationCodeNotException(new String[] {code.text().value()});
    }

    private Verification validateVerificationUrl(Optional<Verification> optionalVerification, Code code, VerificationType type) {
        if(optionalVerification.isEmpty()) {
            throw new VerificationUrlUnAuthorizedException(type.equals(VerificationType.ACCOUNT)? VERIFICATION_ACCOUNT_URL_NOT_FOUND: VERIFICATION_PASSWORD_URL_NOT_FOUND);
        }

        Verification verification = optionalVerification.get();

        if(verification.getStatus() != null && verification.getStatus().equals(VerificationStatus.VERIFIED)) {
            throw new VerificationUrlUnAuthorizedException(type.equals(VerificationType.ACCOUNT)? VERIFICATION_ACCOUNT_URL_ALREADY_VERIFIED: VERIFICATION_PASSWORD_URL_ALREADY_VERIFIED);
        }

        if(verification.getCode() != null && !verification.getCode().text().value().isEmpty() && !verification.getCode().equals(code)) {
            throw new VerificationUrlUnAuthorizedException(type.equals(VerificationType.ACCOUNT)? VERIFICATION_ACCOUNT_URL_INVALID: VERIFICATION_PASSWORD_URL_INVALID);
        }

        if(verification.getExpiredAt() != null && verification.getExpiredAt().isBefore(ZonedDateTime.now())) {
            throw new VerificationCodeUnAuthorizedException(type.equals(VerificationType.ACCOUNT)? VERIFICATION_ACCOUNT_URL_EXPIRED: VERIFICATION_PASSWORD_URL_EXPIRED);
        }

        return verification;
    }

    private Verification validateVerificationCode(Optional<Verification> optionalVerification, Code code, VerificationType type) {
        if(optionalVerification.isEmpty()) {
            throw (type.equals(MFA)? new VerificationCodeUnAuthorizedException(VERIFICATION_MFA_CODE_NOT_FOUND): new VerificationCodeBadException(VERIFICATION_MAIL_SERVER_CODE_NOT_FOUND));
        }

        Verification verification = optionalVerification.get();

        if(verification.getStatus() != null && verification.getStatus().equals(VerificationStatus.VERIFIED)) {
            throw (type.equals(MFA)? new VerificationCodeUnAuthorizedException(VERIFICATION_MFA_CODE_ALREADY_VERIFIED): new VerificationCodeBadException(VERIFICATION_MAIL_SERVER_CODE_ALREADY_VERIFIED));
        }

        if(verification.getCode() != null && !verification.getCode().text().value().isEmpty() && !verification.getCode().equals(code)) {
            throw (type.equals(MFA)? new VerificationCodeUnAuthorizedException(VERIFICATION_MFA_CODE_INVALID): new VerificationCodeBadException(VERIFICATION_MAIL_SERVER_CODE_INVALID));
        }

        if(verification.getExpiredAt() != null && verification.getExpiredAt().isBefore(ZonedDateTime.now())) {
            throw (type.equals(MFA)? new VerificationCodeUnAuthorizedException(VERIFICATION_MFA_CODE_EXPIRED): new VerificationCodeBadException(VERIFICATION_MAIL_SERVER_CODE_EXPIRED));
        }
        return verification;
    }

    private void initializeVerification(Verification verification) {
        if (Objects.requireNonNull(verification.getType()) == VerificationType.PASSWORD || verification.getType() == VerificationType.ACCOUNT) {
            String code = verificationProvider.generateNumericCode(64);
            verification.initializeDefaultsWithUrl(verificationProvider.generateUrl(verification.getUrl(), verification.getType(), Code.of(Text.of(code))), code);
        } else if (verification.getType() == MFA) {
            verification.initializeDefaultsWithCode(verificationProvider.generateNumericCode(6));
        }
    }

    private void cancelExistingVerification(Verification verification) {
        Optional<Verification> optionalVerification;
        if (verification.getUserId() != null) {
            optionalVerification = verificationRepository.findByUserIdAndStatus(verification.getUserId(), VerificationStatus.NOT_VERIFIED);
        } else {
            optionalVerification = verificationRepository.findByMailServerIdAndStatus(verification.getMailServerId(), VerificationStatus.NOT_VERIFIED);
        }

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
