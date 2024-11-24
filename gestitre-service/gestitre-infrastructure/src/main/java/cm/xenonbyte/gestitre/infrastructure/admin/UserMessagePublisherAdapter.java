package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher.UserEventType;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher.UserMessagePublisher;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class UserMessagePublisherAdapter implements UserMessagePublisher {

    private final EventBus eventBus;

    public UserMessagePublisherAdapter(@Nonnull  EventBus eventBus) {
        this.eventBus = Objects.requireNonNull(eventBus);
    }

    @Override
    public void publish(UserCreatedEvent event, UserEventType type) {
        log.info("Publishing event {} for user with name {}  in the bus",
                type.name(), event.getUser().getName().text().value());
        eventBus.publish(type.name(), event);
    }
}
