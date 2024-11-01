package cm.xenonbyte.gestitre.domain.company.event;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.company.entity.Company;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public abstract class CompanyEvent  implements BaseEvent<Company> {

    protected final Company company;
    protected final ZonedDateTime createdAt;

    protected CompanyEvent(Company company, ZonedDateTime createdAt) {
        Assert.field("company", company)
                .notNull();
        Assert.field("Created at", createdAt)
                .notNull();
        this.company = company;
        this.createdAt = createdAt;
    }

    public Company getCompany() {
        return company;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
