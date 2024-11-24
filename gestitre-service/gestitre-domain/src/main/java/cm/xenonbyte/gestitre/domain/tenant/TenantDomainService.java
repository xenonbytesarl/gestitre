package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
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
        tenantRepository.create(tenant);
        LOGGER.info("tenant is created with id " + tenant.getId());
        TenantCreatedEvent tenantCreatedEvent = new TenantCreatedEvent(tenant, ZonedDateTime.now());
        tenantMessagePublisher.publish(tenantCreatedEvent, TENANT_CREATED);
        return tenantCreatedEvent;
    }

    @Override
    public Tenant findByName(@Nonnull Name name) {
        return tenantRepository.findByName(name).orElseThrow(
                () -> new TenantNotFoundException(new String[] {name.text().value()})
        );
    }

    private void validateTenant(Tenant tenant) {
        if(tenant.getId() != null && !tenantRepository.existsById(tenant.getId())) {
            throw new TenantNotFoundException(new String[] {tenant.getId().getValue().toString()});
        }

        if(tenant.getId() == null && tenantRepository.existsByName(tenant.getName())) {
            throw new TenantNameConflictException(new String[] {tenant.getName().text().value()});
        }

        Optional<Tenant> existingTenant = tenantRepository.findByName(tenant.getName());
        if(tenant.getId() != null && existingTenant.isPresent() && !existingTenant.get().getId().equals(tenant.getId())) {
            throw new TenantNameConflictException(new String[] {tenant.getName().text().value()});
        }
    }


}
