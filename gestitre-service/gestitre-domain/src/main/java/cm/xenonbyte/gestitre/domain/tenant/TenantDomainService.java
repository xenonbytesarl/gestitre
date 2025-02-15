package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.company.vo.TenantEventType.TENANT_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */
@DomainEvent
public final class TenantDomainService implements TenantService {

    private static final Logger LOGGER = Logger.getLogger(TenantDomainService.class.getName());

    private final TenantRepository tenantRepository;
    private final TenantMessagePublisher tenantMessagePublisher;

    public TenantDomainService(@Nonnull TenantRepository tenantRepository,
                               @Nonnull TenantMessagePublisher tenantMessagePublisher) {
        this.tenantRepository = Objects.requireNonNull(tenantRepository);
        this.tenantMessagePublisher = Objects.requireNonNull(tenantMessagePublisher);
    }

    @Override
    public TenantCreatedEvent create(Tenant tenant) {
        tenant.validateMandatoryFields();
        validateTenant(tenant);
        tenant.initializeDefaults();
        tenant = tenantRepository.create(tenant);
        LOGGER.info("tenant is created with id " + tenant.getId().getValue());
        TenantCreatedEvent tenantCreatedEvent = new TenantCreatedEvent(tenant, ZonedDateTime.now());
        tenantMessagePublisher.publish(tenantCreatedEvent, TENANT_CREATED);
        return tenantCreatedEvent;
    }

    @Override
    public Tenant findByName(@Nonnull Name name) {
        return tenantRepository.findByName(name).orElse(null);
    }

    @Override
    public Tenant findTenantById(@Nonnull TenantId tenantId) {
        return tenantRepository.findById(tenantId).orElse(null);
    }

    @Override
    public Tenant findByCode(@Nonnull Code code) {
        return tenantRepository.findByCode(code)
                .orElse(null);
    }

    private void validateTenant(Tenant tenant) {
        if(tenant.getId() != null && !tenantRepository.existsById(tenant.getId())) {
            throw new TenantNotFoundException(new String[] {tenant.getId().getValue().toString()});
        }

        validateName(tenant.getId(), tenant.getName());
        validateCode(tenant.getId(), tenant.getCode());
    }

    private void validateCode(TenantId tenantId, Code code) {
        if(tenantId == null && tenantRepository.existsByCode(code)) {
            throw new TenantCodeConflictException(new String[] {code.text().value()});
        }

        Optional<Tenant> existingTenant = tenantRepository.findByCode(code);
        if(tenantId != null && existingTenant.isPresent() && !existingTenant.get().getId().equals(tenantId)) {
            throw new TenantCodeConflictException(new String[] {code.text().value()});
        }
    }

    private void validateName(TenantId tenantId, Name name) {
        if(tenantId == null && tenantRepository.existsByName(name)) {
            throw new TenantNameConflictException(new String[] {name.text().value()});
        }

        Optional<Tenant> existingTenant = tenantRepository.findByName(name);
        if(tenantId != null && existingTenant.isPresent() && !existingTenant.get().getId().equals(tenantId)) {
            throw new TenantNameConflictException(new String[] {name.text().value()});
        }
    }


}
