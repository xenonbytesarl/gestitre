package cm.xenonbyte.gestitre.bootstrap;

import cm.xenonbyte.gestitre.domain.company.CertificateTemplateDomainService;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CertificateTemplateService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CertificateTemplateRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
public final class BootstrapApplicationContext {

    @ApplicationScoped
    public CertificateTemplateService certificateTemplateService(CertificateTemplateRepository certificateTemplateRepository) {
        return new CertificateTemplateDomainService(certificateTemplateRepository);
    }

    @ApplicationScoped
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


}
