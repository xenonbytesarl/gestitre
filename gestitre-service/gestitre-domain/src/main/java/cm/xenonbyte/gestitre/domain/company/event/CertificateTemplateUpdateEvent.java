package cm.xenonbyte.gestitre.domain.company.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@DomainEvent
public final class CertificateTemplateUpdateEvent extends CertificateTemplateEvent {

    public CertificateTemplateUpdateEvent(CertificateTemplate certificateTemplate, ZonedDateTime createdAt) {
        super(certificateTemplate, createdAt);
    }
}
