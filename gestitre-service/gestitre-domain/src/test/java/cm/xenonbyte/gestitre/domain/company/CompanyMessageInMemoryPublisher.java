package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.event.CompanyEvent;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.CompanyMessagePublisher;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public final class CompanyMessageInMemoryPublisher implements CompanyMessagePublisher {

    private Map<CompanyId, Company> companies = new LinkedHashMap<>();


    @Override
    public void publish(CompanyEvent event, CompanyEventType type) {
        companies.put(event.getCompany().getId(), event.getCompany());
    }
}
