package cm.xenonbyte.gestitre.infrastructure.company;

import cm.xenonbyte.gestitre.domain.company.event.CompanyEvent;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.CompanyMessagePublisher;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.DefaultEventBus;
import io.vertx.core.eventbus.EventBus;
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

    public CompanyMessagePublisherAdapter(@DefaultEventBus EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Override
    public void publish(CompanyEvent event, CompanyEventType type) {
        log.info("Publishing event {} for company with name {}  in the bus",
                type.name(), event.getCompany().getCompanyName().text().value());
        eventBus.publish(type.name(), event);
    }



}
