package cm.xenonbyte.gestitre.domain.security;

import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantNotFoundException;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.security.adapter.PasswordInMemoryService;
import cm.xenonbyte.gestitre.domain.security.adapter.RoleInMemoryRepository;
import cm.xenonbyte.gestitre.domain.security.adapter.TenantInMemoryRepository;
import cm.xenonbyte.gestitre.domain.security.adapter.UserInMemoryRepository;
import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.security.ports.primary.PasswordEncryptService;
import cm.xenonbyte.gestitre.domain.security.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.security.vo.Password;
import cm.xenonbyte.gestitre.domain.security.vo.Permission;
import cm.xenonbyte.gestitre.domain.security.vo.PermissionId;
import cm.xenonbyte.gestitre.domain.security.vo.RoleId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.security.RoleNotFoundException.ROLE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.tenant.TenantNotFoundException.TENANT_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.security.UserEmailConflictException.USER_EMAIL_CONFLICT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
class UserDomainServiceTest {

    private UserService userService;
    private RoleId roleId;
    private TenantId tenantId;

    @BeforeEach
    void setUp() {
        UserRepository userRepository = new UserInMemoryRepository();
        RoleRepository roleRepository = new RoleInMemoryRepository();
        TenantRepository tenantRepository = new TenantInMemoryRepository();
        PasswordEncryptService passwordEncryptService = new PasswordInMemoryService();

        tenantId = new TenantId(UUID.fromString("0193127b-1508-7da5-95fe-203d58fa0a97"));
        roleId = new RoleId(UUID.fromString("0193127b-3f4c-710e-8680-f97ba96bd7af"));

        userService = new UserDomainService(
                userRepository,
                roleRepository,
                tenantRepository,
                passwordEncryptService
        );

        tenantRepository.create(
                Tenant.builder()
                        .id(tenantId)
                        .name(Name.of(Text.of("First Tenant")))
                        .build()
        );

        roleRepository.create(
            Role.builder()
                    .id(roleId)
                .name(Name.of(Text.of("Utilisateur")))
                .permissions(
                    Set.of(
                        Permission.builder()
                            .id(new PermissionId(UUID.fromString("0193128d-dcdd-75dd-a586-e473cb938984")))
                            .name(Name.of(Text.of("create:company")))
                            .build(),
                        Permission.builder()
                            .id(new PermissionId(UUID.fromString("0193128e-00c6-760f-b92a-80069e8df541")))
                            .name(Name.of(Text.of("update:company")))
                            .build(),
                        Permission.builder()
                            .id(new PermissionId(UUID.fromString("0193128e-1fe3-75a1-8d65-1fb9a291b5ed")))
                            .name(Name.of(Text.of("read:company")))
                            .build(),
                        Permission.builder()
                            .id(new PermissionId(UUID.fromString("0193128e-3ef1-7e6c-9a5b-2e35f00ef346")))
                            .name(Name.of(Text.of("delete:company")))
                            .build()
                    )
                )
                .active(Active.with(true))
                .build()
        );

        userRepository.create(
            User.builder()
                .name(Name.of(Text.of("Second User")))
                .email(Email.of(Text.of("emailsecond@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .tenantId(tenantId)
                .roleId(roleId)
                .build()
        );
    }

    @Test
    void should_create_new_user() throws Exception {
        //Given
        User user = User.builder()
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("test@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roleId(roleId)
                .tenantId(tenantId)
                .build();

        //Act
        UserCreatedEvent actual = userService.createUser(user);

        //Then
        assertThat(actual).isNotNull();
        assertThat(actual.getUser().getPassword()).isNotNull();
    }

    @Test
    void should_fail_when_create_user_with_not_found_role() {
        //Given
        User user = User.builder()
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("test@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roleId(new RoleId(UUID.randomUUID()))
                .tenantId(tenantId)
                .build();
        //Act + Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage(ROLE_NOT_FOUND);
    }

    @Test
    void should_fail_when_create_user_with_invalid_tenant_name() {
        //Given
        User user = User.builder()
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("test@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roleId(roleId)
                .tenantId(new TenantId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .build();
        //Act + Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(TenantNotFoundException.class)
                .hasMessage(TENANT_NOT_FOUND);
    }

    @Test
    void should_fail_when_create_user_with_duplicate_email() {
        //Given
        User user = User.builder()
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("emailsecond@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roleId(roleId)
                .tenantId(tenantId)
                .build();
        //Act + Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(UserEmailConflictException.class)
                .hasMessage(USER_EMAIL_CONFLICT);
    }
}
