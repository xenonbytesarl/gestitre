package cm.xenonbyte.gestitre.domain.admin.adapter;

import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import jakarta.annotation.Nonnull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class TenantInMemoryRepository implements TenantRepository {

    private Map<TenantId, Tenant> tenants = new LinkedHashMap<>();

    @Override
    public Boolean existsById(@Nonnull TenantId tenantId) {
        return tenants.containsKey(tenantId);
    }

    @Nonnull
    @Override
    public Tenant create(@Nonnull Tenant tenant) {
        tenants.put(tenant.getId(), tenant);
        return tenant;
    }

    @Override
    public Optional<Tenant> findByName(Name name) {
        return tenants.values().stream()
                .filter(tenant ->
                    tenant.getName().text().value().equalsIgnoreCase(name.text().value()))
                .findFirst();
    }

    @Override
    public Boolean existsByName(@Nonnull Name name) {
        return tenants.values().stream().anyMatch(tenant -> tenant.getName().text().value().equalsIgnoreCase(name.text().value()));
    }

    @Override
    public Optional<Tenant> findById(@Nonnull TenantId tenantId) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByCode(@Nonnull Code code) {
        return tenants.values().stream().anyMatch(tenant ->
                tenant.getCode().text().value().equalsIgnoreCase(code.text().value()));
    }

    @Override
    public Optional<Tenant> findByCode(@Nonnull Code code) {
        return tenants.values().stream().filter(tenant ->
                tenant.getCode().text().value().equalsIgnoreCase(code.text().value())).findFirst();
    }
}
