package cm.xenonbyte.gestitre.domain.company.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public abstract class CertificateTemplateEvent implements BaseEvent<CertificateTemplate> {

    protected final CertificateTemplate certificateTemplate;
    protected final ZonedDateTime createdAt;

    protected CertificateTemplateEvent(CertificateTemplate certificateTemplate, ZonedDateTime createdAt) {
        Assert.field("Certificate template", certificateTemplate)
                .notNull();
        Assert.field("Created at", createdAt)
                .notNull();
        this.certificateTemplate = certificateTemplate;
        this.createdAt = createdAt;
    }

    public CertificateTemplate getCertificateTemplate() {
        return certificateTemplate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
