package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.Permission;
import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.admin.User;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "email.text.value", target = "email")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Mapping(source = "password.text.value", target = "password")
    @Mapping(source = "accountEnabled.value", target = "accountEnabled")
    @Mapping(source = "credentialExpired.value", target = "credentialExpired")
    @Mapping(source = "accountLocked.value", target = "accountLocked")
    @Mapping(source = "accountExpired.value", target = "accountExpired")
    @Mapping(source = "useMfa.value", target = "useMfa")
    @Mapping(source = "failedLoginAttempt.value", target = "failedLoginAttempt")
    @Mapping(source = "roles", qualifiedByName = "toRolesJpa", target = "rolesJpa")
    @Nonnull UserJpa toUserJpa(@Nonnull User user);

    @Named("toRolesJpa")
    Set<RoleJpa> toRolesJpas(Set<Role> roles);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "permissions", qualifiedByName = "toPermissionsJpa", target = "permissionsJpa")
    @Mapping(source = "active.value", target = "active")
    @Nonnull RoleJpa toRolesJpa(@Nonnull Role role);

    @Named("toPermissionsJpa")
    Set<PermissionJpa> toPermissionsJpas(Set<Permission> permissions);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Nonnull PermissionJpa toPermissionsJpa(@Nonnull Permission permission);
}
