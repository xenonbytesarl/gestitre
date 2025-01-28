package cm.xenonbyte.gestitre.bootstrap;


import cm.xenonbyte.gestitre.domain.admin.UserDomainService;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.common.verification.VerificationDomainService;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationVerifiedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationRepository;
import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.message.publisher.VerificationMessagePublisher;
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
import cm.xenonbyte.gestitre.domain.notification.MailServerDomainService;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailServerService;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailServerRepository;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.message.MailServerMessagePublisher;
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
import lombok.extern.slf4j.Slf4j;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
@Slf4j
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
            UserMessagePublisher userMessagePublisher,
            TokenProvider tokenProvider
    ) {
        return new UserDomainService(
                userRepository,
                roleRepository,
                tenantService,
                companyService,
                passwordEncryptProvider,
                userMessagePublisher,
                tokenProvider
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
        eventBus.registerDefaultCodec(VerificationCreatedEvent.class, new GenericCodec<>(VerificationCreatedEvent.class));
        eventBus.registerDefaultCodec(VerificationCanceledEvent.class, new GenericCodec<>(VerificationCanceledEvent.class));
        eventBus.registerDefaultCodec(VerificationVerifiedEvent.class, new GenericCodec<>(VerificationVerifiedEvent.class));
        return eventBus;
    }

    @ApplicationScoped
    public VerificationService verificationDomainService(
            VerificationProvider verificationProvider,
            VerificationRepository verificationRepository,
            VerificationMessagePublisher verificationMessagePublisher,
            UserRepository userRepository,
            MailServerRepository mailServerRepository
    ) {
        return new VerificationDomainService(
                verificationProvider,
                verificationRepository,
                verificationMessagePublisher,
                userRepository,
                mailServerRepository
        );
    }

    @ApplicationScoped
    public MailServerService mailServerService(
            MailServerRepository mailServerRepository,
            MailServerMessagePublisher mailServerMessagePublisher,
            VerificationService verificationService
    ) {
        return new MailServerDomainService(mailServerRepository, mailServerMessagePublisher, verificationService);
    }

}
