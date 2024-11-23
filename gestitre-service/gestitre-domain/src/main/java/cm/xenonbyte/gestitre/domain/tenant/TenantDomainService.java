package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.company.ports.secondary.message.TenantEventType.TENANT_CREATED;

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
    public TenantCreateEvent create(Tenant tenant) {
        tenant.validateMandatoryFields();
        validateTenant(tenant);
        tenant.initializeDefaults();
        tenantRepository.create(tenant);
        LOGGER.info("Tenant is created with id " + tenant.getId());
        TenantCreateEvent tenantCreateEvent = new TenantCreateEvent(tenant, ZonedDateTime.now());
        tenantMessagePublisher.publish(tenantCreateEvent, TENANT_CREATED);
        return tenantCreateEvent;
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
