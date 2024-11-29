package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.Verification;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.primary.message.listener.VerificationMessageListener;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Objects;

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
    private final Long codeDuration;
    private final String baseUrl;

    public VerificationMessageListenerAdapter(
            @Nonnull VerificationService verificationService,
            @ConfigProperty(name = "verification.url.duration") Long codeDuration,
            @ConfigProperty(name = "verification.url.baseUrl") String baseUrl
    ) {
        this.verificationService = Objects.requireNonNull(verificationService);
        this.codeDuration = codeDuration;
        this.baseUrl = baseUrl;
    }

    @Override
    @Blocking
    @ConsumeEvent(value = USER_CREATED)
    public void handle(UserCreatedEvent event) {
        log.info(">>>> Receiving event from UserMessagePublisher to create verification for new user with name {}", event.getUser().getName().text().value());
        verificationService.createVerification(
                Verification.builder()
                        .duration(Duration.of(codeDuration))
                        .userId(event.getUser().getId())
                        .email(event.getUser().getEmail())
                        .type(VerificationType.ACCOUNT)
                        .url(Url.of(Text.of(baseUrl)))
                        .build()
        );
    }
}
