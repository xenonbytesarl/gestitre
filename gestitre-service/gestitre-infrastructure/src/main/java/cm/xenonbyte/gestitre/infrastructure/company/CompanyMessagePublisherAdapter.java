package cm.xenonbyte.gestitre.infrastructure.company;

import cm.xenonbyte.gestitre.domain.company.event.CompanyEvent;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.CompanyMessagePublisher;
import io.vertx.mutiny.core.eventbus.EventBus;
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

    private final EventBus bus;

    public CompanyMessagePublisherAdapter(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void publish(CompanyEvent event, CompanyEventType type) {
        log.info("Publishing event {} for company with name {}  in the bus",
                type.name(), event.getCompany().getCompanyName().text().value());
        bus.publish(type.name(), event);
    }
}
