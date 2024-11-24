package cm.xenonbyte.gestitre.bootstrap;


import cm.xenonbyte.gestitre.domain.company.CertificateTemplateDomainService;
import cm.xenonbyte.gestitre.domain.company.CompanyDomainService;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CertificateTemplateService;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.CompanyMessagePublisher;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CompanyRepository;
import cm.xenonbyte.gestitre.domain.file.StorageManagerDomainService;
import cm.xenonbyte.gestitre.domain.file.port.primary.StorageManager;
import cm.xenonbyte.gestitre.domain.security.UserDomainService;
import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.security.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.security.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.TenantDomainService;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.DefaultEventBus;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

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
        mapper.setVisibility(PropertyAccessor.FIELD, ANY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @ApplicationScoped
    public CompanyService companyService(
            CompanyRepository companyRepository,
            CertificateTemplateRepository certificateTemplateRepository,
            CompanyMessagePublisher companyMessagePublisher) {
        return new CompanyDomainService(companyRepository, certificateTemplateRepository, companyMessagePublisher);
    }

    @ApplicationScoped
    public StorageManager storageManager() {
        return new StorageManagerDomainService();
    }

    @ApplicationScoped
    public TenantService tenantService(TenantRepository tenantRepository, TenantMessagePublisher tenantMessagePublisher) {
        return new TenantDomainService(tenantRepository, tenantMessagePublisher);
    }

    @ApplicationScoped
    public UserService userService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            TenantService tenantService,
            CompanyService companyService,
            PasswordEncryptProvider passwordEncryptProvider,
            UserMessagePublisher userMessagePublisher
    ) {
        return new UserDomainService(
                userRepository,
                roleRepository,
                tenantService,
                companyService,
                passwordEncryptProvider,
                userMessagePublisher
        );
    }

    @ApplicationScoped
    @DefaultEventBus
    public EventBus registerCodecs() {
        EventBus eventBus = Vertx.vertx().eventBus();
        eventBus.registerDefaultCodec(CompanyCreatedEvent.class, new GenericCodec<>(CompanyCreatedEvent.class));
        eventBus.registerDefaultCodec(CompanyUpdatedEvent.class, new GenericCodec<>(CompanyUpdatedEvent.class));
        eventBus.registerDefaultCodec(TenantCreatedEvent.class, new GenericCodec<>(TenantCreatedEvent.class));
        eventBus.registerDefaultCodec(UserCreatedEvent.class, new GenericCodec<>(UserCreatedEvent.class));
        return eventBus;
    }


}
