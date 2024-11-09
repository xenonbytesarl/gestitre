package cm.xenonbyte.gestitre.bootstrap;

import cm.xenonbyte.gestitre.domain.company.CertificateTemplateDomainService;
import cm.xenonbyte.gestitre.domain.company.CompanyDomainService;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CertificateTemplateService;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CompanyRepository;
import cm.xenonbyte.gestitre.domain.file.StorageManagerDomainService;
import cm.xenonbyte.gestitre.domain.file.port.primary.StorageManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @ApplicationScoped
    public CompanyService companyService(
            CompanyRepository companyRepository,
            CertificateTemplateRepository certificateTemplateRepository) {
        return new CompanyDomainService(companyRepository, certificateTemplateRepository);
    }

    @ApplicationScoped
    public StorageManager storageManager() {
        return new StorageManagerDomainService();
    }


}
