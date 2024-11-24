package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.domain.security.User;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    @Mapping(source = "roleId", target = "roleId.value")
    @Mapping(expression = "java(createUserViewRequest.getUseMfa() == null? null: cm.xenonbyte.gestitre.domain.security.vo.UseMfa.with(createUserViewRequest.getUseMfa()))", target="useMfa")
    @Nonnull User toUser(@Nonnull @Valid CreateUserViewRequest createUserViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyId", source = "companyId.value")
    @Mapping(target = "tenantId", source = "tenantId.value")
    @Mapping(target = "email", source = "email.text.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "password", source = "password.text.value")
    @Mapping(target = "confirmPassword", source = "confirmPassword.text.value")
    @Mapping(target = "roleId", source = "roleId.value")
    @Mapping(target="useMfa", source = "useMfa.value")
    @Mapping(target="accountExpired", source = "accountExpired.value")
    @Mapping(target="accountLocked", source = "accountLocked.value")
    @Mapping(target="credentialExpired", source = "credentialExpired.value")
    @Mapping(target="accountEnabled", source = "accountEnabled.value")
    @Mapping(target="failedLoginAttempt", source = "failedLoginAttempt.value")
    @Nonnull @Valid CreateUserViewResponse toCreateUserViewResponse(@Nonnull User user);
}
