package cm.xenonbyte.gestitre.application.company.certificatetemplate;

import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindByIdCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindCertificateTemplatesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.SearchCertificateTemplatesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateUpdateEvent;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CertificateTemplateService;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
@ApplicationScoped
public final class CertificateTemplateApplicationAdapterService implements CertificateTemplateApplicationAdapter {

    private final CertificateTemplateService certificateTemplateService;
    private final CertificateTemplateApplicationViewMapper certificateTemplateApplicationViewMapper;

    public CertificateTemplateApplicationAdapterService(
            @Nonnull final CertificateTemplateService certificateTemplateService,
            @Nonnull final CertificateTemplateApplicationViewMapper certificateTemplateApplicationViewMapper
    ) {
        this.certificateTemplateService = Objects.requireNonNull(certificateTemplateService);
        this.certificateTemplateApplicationViewMapper = Objects.requireNonNull(certificateTemplateApplicationViewMapper);
    }

    @Override
    public CreateCertificateTemplateViewResponse createCertificateTemplate(CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest) {
        CertificateTemplateCreatedEvent certificateTemplateCreatedEvent = certificateTemplateService.createCertificate(
                certificateTemplateApplicationViewMapper.toCertificateTemplate(createCertificateTemplateViewRequest));
        return  certificateTemplateApplicationViewMapper.toCreateCertificateTemplateViewResponse(certificateTemplateCreatedEvent.getCertificateTemplate());
    }

    @Override
    public FindByIdCertificateTemplateViewResponse findCertificateTemplateById(UUID certificateTemplateId) {
        CertificateTemplate certificateTemplate = certificateTemplateService.findCertificateById(new CertificateTemplateId(certificateTemplateId));
        return certificateTemplateApplicationViewMapper.toFindByIdCertificateTemplateViewResponse(certificateTemplate);
    }

    @Override
    public FindCertificateTemplatesPageInfoViewResponse findCertificateTemplates(Integer page, Integer size, String field, String direction) {
        PageInfo<CertificateTemplate> pageInfoCertificateTemplate = certificateTemplateService.findCertificates(
                PageInfoPage.of(page), PageInfoSize.of(size), PageInfoField.of(Text.of(field)), PageInfoDirection.valueOf(direction));
        return certificateTemplateApplicationViewMapper.toFindCertificateTemplatesPageInfoViewResponse(pageInfoCertificateTemplate);
    }

    @Nonnull
    @Override
    public SearchCertificateTemplatesPageInfoViewResponse searchCertificateTemplates(Integer page, Integer size, String field, String direction, String keyword) {
        PageInfo<CertificateTemplate> pageInfoCertificateTemplate = certificateTemplateService.searchCertificates(
                PageInfoPage.of(page), PageInfoSize.of(size), PageInfoField.of(Text.of(field)), PageInfoDirection.valueOf(direction), Keyword.of(Text.of(keyword)));
        return certificateTemplateApplicationViewMapper.toSearchCertificateTemplatesPageInfoViewResponse(pageInfoCertificateTemplate);
    }

    @Nonnull
    @Override
    public UpdateCertificateTemplateViewResponse updateCertificateTemplate(UUID certificateTemplateId, UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest) {
        CertificateTemplateUpdateEvent certificateTemplateUpdateEvent = certificateTemplateService.updateCertificates(new CertificateTemplateId(certificateTemplateId), certificateTemplateApplicationViewMapper.toCertificateTemplate(updateCertificateTemplateViewRequest));
        return certificateTemplateApplicationViewMapper.toUpdateCertificateTemplateViewResponse(certificateTemplateUpdateEvent.getCertificateTemplate());
    }
}
