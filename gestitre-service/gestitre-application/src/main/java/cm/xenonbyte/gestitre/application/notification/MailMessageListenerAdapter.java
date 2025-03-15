package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.vo.Subject;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.Mail;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailServerService;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailService;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.message.listener.MailMessageListener;
import cm.xenonbyte.gestitre.domain.notification.vo.MailState;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.MultiMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.VERIFICATION_CREATED;
import static cm.xenonbyte.gestitre.domain.notification.Mail.MAIL_DEFAULT_ACTIVATE_ACCOUNT_SUBJECT;
import static cm.xenonbyte.gestitre.domain.notification.Mail.MAIL_DEFAULT_MFA_SUBJECT;
import static cm.xenonbyte.gestitre.domain.notification.Mail.MAIL_DEFAULT_RESET_PASSWORD_SUBJECT;
import static cm.xenonbyte.gestitre.domain.notification.Mail.MAIL_DEFAULT_VERIFY_MAIL_SERVER_SUBJECT;
import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailMessageListenerAdapter implements MailMessageListener {

    private final MailService mailService;
    private final UserService userService;
    private final MailServerService mailServerService;
    @ConfigProperty(name = "verification.code.duration")
    Long codeDuration;
    @ConfigProperty(name = "verification.url.duration")
    Long urlDuration;
    @ConfigProperty(name = "verification.url.baseUrl")
    String baseURL;


    public MailMessageListenerAdapter(
            final MailService mailService,
            final UserService userService,
            final MailServerService mailServerService) {
        this.mailService = requireNonNull(mailService);
        this.userService = requireNonNull(userService);
        this.mailServerService = requireNonNull(mailServerService);
    }

    @Blocking
    @Transactional
    @ConsumeEvent(value = VERIFICATION_CREATED)
    public void handleVerificationCreatedEvent(MultiMap headers, VerificationCreatedEvent event) {
        log.info(">>>> Receiving event from VerificationMessagePublisher to create email of type {}", event.getVerification().getType());
        //TenantContext.set(UUID.fromString(headers.get("tenantId")));
        handle(event);
    }

    @Override
    public void handle(VerificationCreatedEvent event) {
        Mail mail = buildEmail(event.getVerification());
        mailService.createMail(mail);
    }

    private Mail buildEmail(Verification verification) {
        return switch (verification.getType()) {
            case MFA -> buildMfaMail(verification);
            case ACCOUNT -> buildAccountMail(verification);
            case PASSWORD -> buildPasswordMail(verification);
            case MAIL_SERVER -> buildMailServerMail(verification);
        };
    }

    private Mail buildMailServerMail(Verification verification) {
        MailServer mailServer = mailServerService.findMailServerById(verification.getMailServerId());
        return  Mail.builder()
                .to(mailServer.getFrom())
                .type(MailTemplateType.VERIFY_MAIL_SERVER)
                .state(MailState.SENDING)
                .subject(Subject.of(Text.of(MAIL_DEFAULT_VERIFY_MAIL_SERVER_SUBJECT)))
                .attributes(Map.of(
                        Text.of("owner"), mailServer.getName().text(),
                        Text.of("code"), verification.getCode().text(),
                        Text.of("expireIn"), Text.of(String.valueOf(codeDuration / 60))
                ))
                .build();
    }

    private Mail buildPasswordMail(Verification verification) {
        User user = userService.findUserById(verification.getUserId());
        String verificationUrl = String.format("%s/%s", baseURL, verification.getUrl().text().value());
        return  Mail.builder()
                .to(user.getEmail())
                .type(MailTemplateType.RESET_PASSWORD)
                .state(MailState.SENDING)
                .subject(Subject.of(Text.of(MAIL_DEFAULT_RESET_PASSWORD_SUBJECT)))
                .attributes(Map.of(
                        Text.of("fullName"), user.getName().text(),
                        Text.of("verificationUrl"), Text.of(verificationUrl),
                        Text.of("expireIn"), Text.of(String.valueOf(urlDuration / 86400))
                ))
                .build();
    }

    private Mail buildAccountMail(Verification verification) {
        User user = userService.findUserById(verification.getUserId());
        String verificationUrl = String.format("%s/%s", baseURL, verification.getUrl().text().value());
        return  Mail.builder()
                .to(user.getEmail())
                .type(MailTemplateType.ACTIVATE_ACCOUNT)
                .state(MailState.SENDING)
                .subject(Subject.of(Text.of(MAIL_DEFAULT_ACTIVATE_ACCOUNT_SUBJECT)))
                .attributes(Map.of(
                        Text.of("fullName"), user.getName().text(),
                        Text.of("verificationUrl"), Text.of(verificationUrl),
                        Text.of("expireIn"), Text.of(String.valueOf(urlDuration / 86400))
                ))
                .build();
    }

    private Mail buildMfaMail(Verification verification) {
        User user = userService.findUserById(verification.getUserId());

        return Mail.builder()
                .to(user.getEmail())
                .type(MailTemplateType.MFA)
                .state(MailState.SENDING)
                .subject(Subject.of(Text.of(MAIL_DEFAULT_MFA_SUBJECT)))
                .attributes(Map.of(
                        Text.of("fullName"), user.getName().text(),
                        Text.of("code"), verification.getCode().text(),
                        Text.of("expireIn"), Text.of(String.valueOf(codeDuration / 60))
                ))
                .build();
    }
}
