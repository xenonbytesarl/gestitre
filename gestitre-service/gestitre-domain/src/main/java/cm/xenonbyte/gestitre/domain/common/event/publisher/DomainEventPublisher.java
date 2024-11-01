package cm.xenonbyte.gestitre.domain.common.event.publisher;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;

public interface DomainEventPublisher<T extends BaseEvent> {

    void publish(T domainEvent);
}
