package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
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
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@DomainService
public final class CertificateTemplateDomainService implements CertificateTemplateService {

    private static final Logger LOGGER = Logger.getLogger(CertificateTemplateDomainService.class.getName());

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
        certificateTemplate = certificateTemplateRepository.create(certificateTemplate);
        LOGGER.info("Certificate template created with id " + certificateTemplate.getId().getValue());
        //TODO fire an event to audit manager
        return new CertificateTemplateCreatedEvent(certificateTemplate, ZonedDateTime.now());
    }

    @Nonnull
    @Override
    public CertificateTemplate findCertificateById(@Nonnull CertificateTemplateId certificateTemplateId) {
        CertificateTemplate certificateTemplate = certificateTemplateRepository.findById(certificateTemplateId).orElseThrow(
                () -> new CertificateTemplateNotFoundException(new String[]{certificateTemplateId.getValue().toString()})
        );
        LOGGER.info("Certificate template found with id " + certificateTemplateId.getValue());
        return certificateTemplate;
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> findCertificates(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection
    ) {
        validatePageInfoParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        PageInfo<CertificateTemplate> certificateTemplatePageInfo =
                certificateTemplateRepository.findAll(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        LOGGER.info("Found " + certificateTemplatePageInfo.getTotalElements() + " certificates");
        return certificateTemplatePageInfo;
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> searchCertificates(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection,
            @Nonnull Keyword keyword
    ) {
        validatePageInfoParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        Assert.field("Keyword", keyword).notNull();
        PageInfo<CertificateTemplate> certificateTemplatePageInfo =
                certificateTemplateRepository.search(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
        LOGGER.info("Found " + certificateTemplatePageInfo.getTotalElements() + " certificates for keyword " + keyword.text().value());
        return certificateTemplatePageInfo;
    }

    @Nonnull
    @Override
    public CertificateTemplateUpdateEvent updateCertificates(
            @Nonnull CertificateTemplateId certificateTemplateId,
            @Nonnull CertificateTemplate newCertificateTemplate) {
        newCertificateTemplate.validateMandatoryFields();
        findCertificateById(certificateTemplateId);
        validateCertificateTemplate(newCertificateTemplate);
        newCertificateTemplate = certificateTemplateRepository.update(certificateTemplateId, newCertificateTemplate);
        LOGGER.info("Certificate template updated with id " + newCertificateTemplate.getId().getValue());
        return new CertificateTemplateUpdateEvent(newCertificateTemplate, ZonedDateTime.now());
    }

    private void validateCertificateTemplate(CertificateTemplate certificateTemplate) {
        validateCertificateName(certificateTemplate.getId(), certificateTemplate.getName());
    }

    private void validateCertificateName(CertificateTemplateId certificateTemplateId, Name name) {
        if(certificateTemplateId == null && certificateTemplateRepository.existsByName(name)) {
            throw new CertificateTemplateNameConflictException(new String[] {name.text().value()});
        }

        Optional<CertificateTemplate> oldCertificateTemplate = certificateTemplateRepository.findByName(name);
        if(certificateTemplateId != null && oldCertificateTemplate.isPresent() && !oldCertificateTemplate.get().getId().equals(certificateTemplateId)) {
            throw new CertificateTemplateNameConflictException(new String[] {name.text().value()});
        }
    }

    private static void validatePageInfoParameters(
            PageInfoPage pageInfoPage,
            PageInfoSize pageInfoSize,
            PageInfoField pageInfoField,
            PageInfoDirection pageInfoDirection) {
        Assert.field("Page", pageInfoPage).notNull();
        Assert.field("Size", pageInfoSize).notNull();
        Assert.field("Field", pageInfoField).notNull();
        Assert.field("Direction", pageInfoDirection).notNull();
    }
}
