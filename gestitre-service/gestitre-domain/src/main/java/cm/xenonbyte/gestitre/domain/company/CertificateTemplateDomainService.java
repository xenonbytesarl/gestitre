package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Direction;
import cm.xenonbyte.gestitre.domain.common.vo.Field;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Page;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Size;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateUpdateEvent;
import cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNameConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CertificateTemplateService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@DomainService
public final class CertificateTemplateDomainService implements CertificateTemplateService {

    private final CertificateTemplateRepository certificateTemplateRepository;

    public CertificateTemplateDomainService(@Nonnull CertificateTemplateRepository certificateTemplateRepository) {
        this.certificateTemplateRepository = Objects.requireNonNull(certificateTemplateRepository);
    }

    @Nonnull
    @Override
    public CertificateTemplateCreatedEvent createCertificate(@Nonnull CertificateTemplate certificateTemplate) {
        certificateTemplate.validateMandatoryFields();
        validateCertificateTemplate(certificateTemplate);
        certificateTemplate.initializeDefaultValues();
        certificateTemplate = certificateTemplateRepository.save(certificateTemplate);
        return new CertificateTemplateCreatedEvent(certificateTemplate, ZonedDateTime.now());
    }

    @Nonnull
    @Override
    public CertificateTemplate findCertificateById(@Nonnull CertificateTemplateId certificateTemplateId) {
        return certificateTemplateRepository.findById(certificateTemplateId).orElseThrow(
                () -> new CertificateTemplateNotFoundException(new String[] {certificateTemplateId.toString()})
        );
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> findCertificates(
            @Nonnull Page page,
            @Nonnull Size size,
            @Nonnull Field field,
            @Nonnull Direction direction
    ) {
        Assert.field("Page", page).notNull();
        Assert.field("Size", size).notNull();
        Assert.field("Field", field).notNull();
        Assert.field("Direction", direction).notNull();

        return certificateTemplateRepository.findAll(page, size, field, direction);
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> searchCertificates(
            @Nonnull Page page,
            @Nonnull Size size,
            @Nonnull Field field,
            @Nonnull Direction direction,
            @Nonnull Keyword keyword
    ) {
        Assert.field("Page", page).notNull();
        Assert.field("Size", size).notNull();
        Assert.field("Field", field).notNull();
        Assert.field("Direction", direction).notNull();
        Assert.field("Keyword", keyword).notNull();
        return certificateTemplateRepository.search(page, size, field, direction, keyword);
    }

    @Nonnull
    @Override
    public CertificateTemplateUpdateEvent updateCertificates(
            @Nonnull CertificateTemplateId certificateTemplateId,
            @Nonnull CertificateTemplate newCertificateTemplate) {
        newCertificateTemplate.validateMandatoryFields();
        CertificateTemplate oldCertificateTemplate = findCertificateById(certificateTemplateId);
        validateCertificateTemplate(newCertificateTemplate);
        newCertificateTemplate = certificateTemplateRepository.updateCertificateTemplate(oldCertificateTemplate, newCertificateTemplate);
        return new CertificateTemplateUpdateEvent(newCertificateTemplate, ZonedDateTime.now());
    }

    private void validateCertificateTemplate(CertificateTemplate certificateTemplate) {
        validateCertificateName(certificateTemplate.getId(), certificateTemplate.getName());
    }

    private void validateCertificateName(CertificateTemplateId certificateTemplateId, Name name) {
        if(certificateTemplateId == null && certificateTemplateRepository.existsByName(name)) {
            throw new CertificateTemplateNameConflictException(new String[] {name.text().value()});
        }
    }
}
