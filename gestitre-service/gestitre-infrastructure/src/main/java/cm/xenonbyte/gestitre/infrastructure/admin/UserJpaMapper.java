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
    @Mapping(expression = "java(cm.xenonbyte.gestitre.infrastructure.company.CompanyJpa.builder().id(user.getCompanyId().getValue()).build())", target = "companyJpa")
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

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "name.text.value", source = "name")
    @Mapping(target = "email.text.value", source = "email")
    @Mapping(target = "tenantId.value", source = "tenantId")
    @Mapping(target = "companyId", expression = "java(new cm.xenonbyte.gestitre.domain.common.vo.CompanyId(userJpa.getCompanyJpa().getId()))")
    @Mapping(target = "password.text.value", source = "password")
    @Mapping(target = "accountEnabled.value", source = "accountEnabled")
    @Mapping(target = "credentialExpired.value", source = "credentialExpired")
    @Mapping(target = "accountLocked.value", source = "accountLocked")
    @Mapping(target = "accountExpired.value", source = "accountExpired")
    @Mapping(target = "useMfa.value", source = "useMfa")
    @Mapping(target = "failedLoginAttempt.value", source = "failedLoginAttempt")
    @Mapping(target = "roles", qualifiedByName = "toRoles", source = "rolesJpa")
    @Nonnull User toUser(@Nonnull UserJpa userJpa);

    @Named("toRoles")
    Set<Role> toRoles(Set<RoleJpa> rolesJpa);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "name.text.value", source = "name")
    @Mapping(target = "permissions", qualifiedByName = "toPermissions", source = "permissionsJpa")
    @Mapping(target = "active.value", source = "active")
    @Nonnull Role toRole(@Nonnull RoleJpa roleJpa);

    @Named("toPermissions")
    Set<Permission> toPermissions(Set<PermissionJpa> permissionsJpa);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "name.text.value", source = "name")
    @Nonnull Permission toPermission(@Nonnull PermissionJpa permissionJpa);
}
