package cm.xenonbyte.gestitre.domain.company.ports.secondary.message;

import cm.xenonbyte.gestitre.domain.company.event.CompanyEvent;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
public interface CompanyMessagePublisher {
    void publish(CompanyEvent event, CompanyEventType type);
}
