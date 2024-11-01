package cm.xenonbyte.gestitre.domain.company.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.company.entity.Company;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@DomainEvent
public final  class CompanyCreatedEvent extends CompanyEvent {

    public CompanyCreatedEvent(Company company, ZonedDateTime createdAt) {
        super(company, createdAt);
    }
}
