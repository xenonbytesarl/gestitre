package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.security.User;
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
public interface UserJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "email.text.value", target = "email")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.infrastructure.admin.RoleJpa.builder().id(user.getRoleId().getValue()).build())", target = "roleJpa")
    @Mapping(source = "tenantId.value", target = "tenantId")
    @Mapping(source = "password.text.value", target = "password")
    @Mapping(source = "accountEnabled.value", target = "accountEnabled")
    @Mapping(source = "credentialExpired.value", target = "credentialExpired")
    @Mapping(source = "accountLocked.value", target = "accountLocked")
    @Mapping(source = "accountExpired.value", target = "accountExpired")
    @Mapping(source = "useMfa.value", target = "useMfa")
    @Mapping(source = "failedLoginAttempt.value", target = "failedLoginAttempt")
    @Nonnull UserJpa toUserJpa(@Nonnull User user);
}
