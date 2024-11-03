package cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate;

import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CertificateTemplateJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "active.value", target = "active")
    @Nonnull CertificateTemplateJpa toCertificateTemplateJpa(@Nonnull CertificateTemplate certificateTemplate);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "name.text.value", source = "name")
    @Mapping(target = "active.value", source = "active")
    CertificateTemplate toCertificateTemplate(CertificateTemplateJpa certificateTemplateJpa);

    void copyNewToOldCertificateTemplateJpa(@Nonnull CertificateTemplateJpa newCertificateTemplateJpa, @Nonnull @MappingTarget CertificateTemplateJpa oldCertificateTemplateJpa);
}
