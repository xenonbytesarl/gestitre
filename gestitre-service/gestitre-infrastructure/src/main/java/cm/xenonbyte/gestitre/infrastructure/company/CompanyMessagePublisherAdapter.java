package cm.xenonbyte.gestitre.infrastructure.company;

import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.CompanyMessagePublisher;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType;
import io.vertx.core.eventbus.EventBus;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;


/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class CompanyMessagePublisherAdapter implements CompanyMessagePublisher {

    private final EventBus eventBus;

    public CompanyMessagePublisherAdapter(@Nonnull  EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Override
    public void publish(CompanyCreatedEvent event, CompanyEventType type) {
        log.info("Publishing event {} for company with name {}  in the bus",
                type.name(), event.getCompany().getCompanyName().text().value());
        eventBus.publish(type.name(), event);
    }

    @Override
    public void publish(CompanyUpdatedEvent event, CompanyEventType type) {
        log.info("Publishing event {} for company with name {}  in the bus",
                type.name(), event.getCompany().getCompanyName().text().value());
        eventBus.publish(type.name(), event);
    }


}
