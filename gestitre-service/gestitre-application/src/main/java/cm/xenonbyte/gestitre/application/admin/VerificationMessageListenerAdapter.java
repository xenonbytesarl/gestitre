package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.common.verification.ports.primary.message.listener.VerificationMessageListener;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;
import cm.xenonbyte.gestitre.domain.context.TenantContext;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.MultiMap;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.MAIL_SERVER_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.USER_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@Slf4j
@ApplicationScoped
public final class VerificationMessageListenerAdapter implements VerificationMessageListener {

    private final VerificationService verificationService;
    private final VerificationProvider verificationProvider;
    private final Long urlCodeDuration;
    private final Long mailServerCodeDuration;
    private final String baseUrl;

    public VerificationMessageListenerAdapter(
            @Nonnull VerificationService verificationService,
            @Nonnull VerificationProvider verificationProvider,
            @ConfigProperty(name = "verification.url.duration") Long urlCodeDuration,
            @ConfigProperty(name = "verification.mailServer.duration") Long mailServerCodeDuration,
            @ConfigProperty(name = "verification.url.baseUrl") String baseUrl
    ) {
        this.verificationService = Objects.requireNonNull(verificationService);
        this.verificationProvider = Objects.requireNonNull(verificationProvider);
        this.urlCodeDuration = urlCodeDuration;
        this.mailServerCodeDuration = mailServerCodeDuration;
        this.baseUrl = baseUrl;
    }

    @Blocking
    @ConsumeEvent(value = USER_CREATED)
    public void handleUserCreatedEvent(MultiMap headers, UserCreatedEvent event) {
        log.info(">>>> Receiving event from UserMessagePublisher to create verification for new user with name {}", event.getUser().getName().text().value());
        TenantContext.set(UUID.fromString(headers.get("tenantId")));
        handle(event);
    }

    @Override
    public void handle(UserCreatedEvent event) {
        verificationService.createVerification(
                Verification.builder()
                        .duration(Duration.of(urlCodeDuration))
                        .userId(event.getUser().getId())
                        .email(event.getUser().getEmail())
                        .type(VerificationType.ACCOUNT)
                        .url(Url.of(Text.of(baseUrl)))
                        .build()
        );
    }

    @Blocking
    @ConsumeEvent(value = MAIL_SERVER_CREATED)
    public void handleMailServerCreatedEvent(MultiMap headers, MailServerCreatedEvent event) {
        log.info(">>>> Receiving event from MailServerMessagePublisher to create verification for new mail server with name {}", event.getMailServer().getName().text().value());
        TenantContext.set(UUID.fromString(headers.get("tenantId")));
        handle(event);
    }

    @Override
    public void handle(MailServerCreatedEvent event) {
        String code = verificationProvider.generateNumericCode(64);
        verificationService.createVerification(
                Verification.builder()
                        .duration(Duration.of(mailServerCodeDuration))
                        .mailServerId(event.getMailServer().getId())
                        .email(event.getMailServer().getFrom())
                        .type(VerificationType.MAIL_SERVER)
                        .code(Code.of(Text.of(code)))
                        .build()
        );
    }





}
