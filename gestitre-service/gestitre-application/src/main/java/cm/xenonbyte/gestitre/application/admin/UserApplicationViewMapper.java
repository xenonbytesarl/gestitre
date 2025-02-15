package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.ActivateUserResponse;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.FindUserByIdViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.PermissionView;
import cm.xenonbyte.gestitre.application.admin.dto.RoleView;
import cm.xenonbyte.gestitre.application.admin.dto.SearchUserPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.SearchUsersViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewResponse;
import cm.xenonbyte.gestitre.domain.admin.Permission;
import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserApplicationViewMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "companyId", target = "companyId.value")
    @Mapping(source = "email", target = "email.text.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "password", target = "password.text.value")
    @Mapping(source = "confirmPassword", target = "confirmPassword.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.admin.vo.Timezone.valueOf(createUserViewRequest.getTimezoneView().name()))", target = "timezone")
    @Mapping(expression = "java(createUserViewRequest.getUseMfa() == null? null: cm.xenonbyte.gestitre.domain.admin.vo.UseMfa.with(createUserViewRequest.getUseMfa()))", target="useMfa")
    @Mapping(source = "roleViews", qualifiedByName = "toRoles", target = "roles")
    @Nonnull User toUser(@Nonnull @Valid CreateUserViewRequest createUserViewRequest);

    @Named("toRoles")
    Set<Role> toRoles(@Nonnull @Valid Set<RoleView> roleViews);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "active", target = "active.value")
    @Mapping(source = "permissionViews", qualifiedByName = "toPermissions", target = "permissions")
    @Nonnull Role toRole(@Nonnull @Valid RoleView roleView);

    @Named("toPermissions")
    Set<Permission> toPermissions(@Nonnull @Valid Set<PermissionView> permissionViews);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "name", target = "name.text.value")
    @Nonnull Permission toPermission(@Nonnull @Valid PermissionView permissionView);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyId", source = "companyId.value")
    @Mapping(target = "tenantId", source = "tenantId.value")
    @Mapping(target = "email", source = "email.text.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "timezoneView", expression = "java(cm.xenonbyte.gestitre.application.admin.dto.TimezoneView.valueOf(user.getTimezone().name()))")
    @Mapping(target="useMfa", source = "useMfa.value")
    @Mapping(target="accountExpired", source = "accountExpired.value")
    @Mapping(target="accountLocked", source = "accountLocked.value")
    @Mapping(target="credentialExpired", source = "credentialExpired.value")
    @Mapping(target="accountEnabled", source = "accountEnabled.value")
    @Mapping(target="failedLoginAttempt", source = "failedLoginAttempt.value")
    @Mapping(target = "roleViews", qualifiedByName = "toRoleViews", source = "roles")
    @Nonnull @Valid CreateUserViewResponse toCreateUserViewResponse(@Nonnull User user);

    @Named("toRoleViews")
    Set<RoleView> toRoleViews(@Nonnull Set<Role> roles);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Mapping(target = "permissionViews", qualifiedByName = "toPermissionViews", source = "permissions")
    @Nonnull @Valid RoleView toRoleView(@Nonnull Role role);

    @Named("toPermissionViews")
    Set<PermissionView> toPermissionViews(@Nonnull @Valid Set<Permission> permissions);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Nonnull PermissionView toPermissionView(@Nonnull @Valid Permission permission);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyId", source = "companyId.value")
    @Mapping(target = "tenantId", source = "tenantId.value")
    @Mapping(target = "email", source = "email.text.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "timezoneView", expression = "java(cm.xenonbyte.gestitre.application.admin.dto.TimezoneView.valueOf(user.getTimezone().name()))")
    @Mapping(target="useMfa", source = "useMfa.value")
    @Mapping(target="accountExpired", source = "accountExpired.value")
    @Mapping(target="accountLocked", source = "accountLocked.value")
    @Mapping(target="credentialExpired", source = "credentialExpired.value")
    @Mapping(target="accountEnabled", source = "accountEnabled.value")
    @Mapping(target="failedLoginAttempt", source = "failedLoginAttempt.value")
    @Mapping(target = "roleViews", qualifiedByName = "toRoleViews", source = "roles")
    @Nonnull @Valid ActivateUserResponse toActivateUserResponse(@Nonnull User user);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyId", source = "companyId.value")
    @Mapping(target = "tenantId", source = "tenantId.value")
    @Mapping(target = "email", source = "email.text.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "timezoneView", expression = "java(cm.xenonbyte.gestitre.application.admin.dto.TimezoneView.valueOf(user.getTimezone().name()))")
    @Mapping(target="useMfa", source = "useMfa.value")
    @Mapping(target="accountExpired", source = "accountExpired.value")
    @Mapping(target="accountLocked", source = "accountLocked.value")
    @Mapping(target="credentialExpired", source = "credentialExpired.value")
    @Mapping(target="accountEnabled", source = "accountEnabled.value")
    @Mapping(target="failedLoginAttempt", source = "failedLoginAttempt.value")
    @Mapping(target = "roleViews", qualifiedByName = "toRoleViews", source = "roles")
    @Nonnull @Valid FindUserByIdViewResponse toFindUserByIdViewResponse(@Nonnull User user);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "companyId", target = "companyId.value")
    @Mapping(source = "email", target = "email.text.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.admin.vo.Timezone.valueOf(updateUserViewRequest.getTimezoneView().name()))", target = "timezone")
    @Mapping(expression = "java(updateUserViewRequest.getUseMfa() == null? null: cm.xenonbyte.gestitre.domain.admin.vo.UseMfa.with(updateUserViewRequest.getUseMfa()))", target="useMfa")
    @Mapping(source="useMfa", target = "useMfa.value")
    @Mapping(source="accountExpired", target = "accountExpired.value")
    @Mapping(source="accountLocked", target = "accountLocked.value")
    @Mapping(source="credentialExpired", target = "credentialExpired.value")
    @Mapping(source="accountEnabled", target = "accountEnabled.value")
    @Mapping(source="failedLoginAttempt", target = "failedLoginAttempt.value")
    @Mapping(source = "roleViews", qualifiedByName = "toRoles", target = "roles")
    @Nonnull User toUser(@Nonnull @Valid UpdateUserViewRequest updateUserViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyId", source = "companyId.value")
    @Mapping(target = "tenantId", source = "tenantId.value")
    @Mapping(target = "email", source = "email.text.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "timezoneView", expression = "java(cm.xenonbyte.gestitre.application.admin.dto.TimezoneView.valueOf(user.getTimezone().name()))")
    @Mapping(target="useMfa", source = "useMfa.value")
    @Mapping(target="accountExpired", source = "accountExpired.value")
    @Mapping(target="accountLocked", source = "accountLocked.value")
    @Mapping(target="credentialExpired", source = "credentialExpired.value")
    @Mapping(target="accountEnabled", source = "accountEnabled.value")
    @Mapping(target="failedLoginAttempt", source = "failedLoginAttempt.value")
    @Mapping(target = "roleViews", qualifiedByName = "toRoleViews", source = "roles")
    @Nonnull @Valid UpdateUserViewResponse toUpdateUserViewResponse(@Nonnull User user);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchUsersViewResponses", target = "elements")
    @Nonnull @Valid SearchUserPageInfoViewResponse toSearchUsersPageInfoViewResponse(@Nonnull PageInfo<User> userPageInfo);

    @Named("toSearchUsersViewResponses")
    @Nonnull @Valid List<SearchUsersViewResponse> toSearchUsersViewResponses(@Nonnull List<User> users);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyId", source = "companyId.value")
    @Mapping(target = "tenantId", source = "tenantId.value")
    @Mapping(target = "email", source = "email.text.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "timezoneView", expression = "java(cm.xenonbyte.gestitre.application.admin.dto.TimezoneView.valueOf(user.getTimezone().name()))")
    @Mapping(target="useMfa", source = "useMfa.value")
    @Mapping(target="accountExpired", source = "accountExpired.value")
    @Mapping(target="accountLocked", source = "accountLocked.value")
    @Mapping(target="credentialExpired", source = "credentialExpired.value")
    @Mapping(target="accountEnabled", source = "accountEnabled.value")
    @Mapping(target="failedLoginAttempt", source = "failedLoginAttempt.value")
    @Mapping(target = "roleViews", qualifiedByName = "toRoleViews", source = "roles")
    @Nonnull @Valid SearchUsersViewResponse toSearchUsersViewResponse(@Nonnull User user);


}
