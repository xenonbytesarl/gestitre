package cm.xenonbyte.gestitre.infrastructure.admin.verification;

import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.message.publisher.VerificationMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationEventType;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@Slf4j
@ApplicationScoped
public final class VerificationMessagePublisherAdapter implements VerificationMessagePublisher {

    private final EventBus eventBus;

    public VerificationMessagePublisherAdapter(@Nonnull EventBus eventBus) {
        this.eventBus = Objects.requireNonNull(eventBus);
    }

    @Override
    public void publish(VerificationEvent event, VerificationEventType type) {
        log.info("Publishing event {} for type {} in the bus",
                type.name(), event.getVerification().getType().name());
        eventBus.publish(type.name(), event);
    }

}
