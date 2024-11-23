package cm.xenonbyte.gestitre.infrastructure.Tenant;

import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface TenantJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    TenantJpa toTenantJpa(Tenant tenant);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "active", target = "active.value")
    @Nonnull Tenant toTenant(@Nonnull TenantJpa tenantJpa);

}
