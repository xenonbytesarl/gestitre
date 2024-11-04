package cm.xenonbyte.gestitre.application.company.certificatetemplate;


import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindByIdCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindCertificateTemplatesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.SearchCertificateTemplatesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
public interface CertificateTemplateApplicationAdapter {
    @Nonnull @Valid CreateCertificateTemplateViewResponse createCertificateTemplate(CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest);

    @Nonnull @Valid FindByIdCertificateTemplateViewResponse findCertificateTemplateById(UUID certificateTemplateId);

    @Nonnull @Valid FindCertificateTemplatesPageInfoViewResponse findCertificateTemplates(Integer page, Integer size, String field, String direction);

    @Nonnull @Valid SearchCertificateTemplatesPageInfoViewResponse searchCertificateTemplates(Integer page, Integer size, String field, String direction, String keyword);

    @Nonnull @Valid UpdateCertificateTemplateViewResponse updateCertificateTemplate(UUID certificateTemplateId, UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest);
}
