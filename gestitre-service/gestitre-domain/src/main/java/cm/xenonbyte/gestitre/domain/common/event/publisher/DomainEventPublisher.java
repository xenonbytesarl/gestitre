package cm.xenonbyte.gestitre.domain.common.event.publisher;

import cm.xenonbyte.gestitre.domain.common.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
