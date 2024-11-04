package cm.xenonbyte.gestitre.application.company.certificatetemplate;

import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindByIdCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindCertificateTemplatesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.FindCertificateTemplatesViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.SearchCertificateTemplatesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.SearchCertificateTemplatesViewResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */

@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CertificateTemplateApplicationViewMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name.text.value")
    @Nonnull CertificateTemplate toCertificateTemplate(@Nonnull @Valid CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid CreateCertificateTemplateViewResponse toCreateCertificateTemplateViewResponse(@Nonnull CertificateTemplate certificateTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid FindByIdCertificateTemplateViewResponse toFindByIdCertificateTemplateViewResponse(@Nonnull CertificateTemplate certificateTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toFindCertificateTemplatesViewResponses", target = "elements")
    @Nonnull @Valid FindCertificateTemplatesPageInfoViewResponse toFindCertificateTemplatesPageInfoViewResponse(@Nonnull PageInfo<CertificateTemplate> pageInfoCertificateTemplate);

    @Named("toFindCertificateTemplatesViewResponses")
    @Nonnull @Valid List<FindCertificateTemplatesViewResponse> toFindCertificateTemplatesViewResponses(@Nonnull List<CertificateTemplate> certificateTemplates);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid FindCertificateTemplatesViewResponse toFindCertificateTemplatesViewResponse(@Nonnull CertificateTemplate certificateTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchCertificateTemplatesViewResponses", target = "elements")
    @Nonnull @Valid SearchCertificateTemplatesPageInfoViewResponse toSearchCertificateTemplatesPageInfoViewResponse(@Nonnull PageInfo<CertificateTemplate> pageInfoCertificateTemplate);


    @Named("toSearchCertificateTemplatesViewResponses")
    @Nonnull @Valid List<SearchCertificateTemplatesViewResponse> toSearchCertificateTemplatesViewResponses(@Nonnull List<CertificateTemplate> certificateTemplates);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid SearchCertificateTemplatesViewResponse toSearchCertificateTemplatesViewResponse(@Nonnull CertificateTemplate certificateTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "active", target = "active.value")
    @Nonnull CertificateTemplate toCertificateTemplate(@Nonnull @Valid UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid UpdateCertificateTemplateViewResponse toUpdateCertificateTemplateViewResponse(@Nonnull CertificateTemplate certificateTemplate);
}
