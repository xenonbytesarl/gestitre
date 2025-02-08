package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.admin.adapter.PasswordInMemoryProvider;
import cm.xenonbyte.gestitre.domain.admin.adapter.RoleInMemoryRepository;
import cm.xenonbyte.gestitre.domain.admin.adapter.TenantInMemoryRepository;
import cm.xenonbyte.gestitre.domain.admin.adapter.TokenInMemoryProvider;
import cm.xenonbyte.gestitre.domain.admin.adapter.UserInMemoryRepository;
import cm.xenonbyte.gestitre.domain.admin.adapter.UserMessageInMemoryPublisher;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountEnabled;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountExpired;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountLocked;
import cm.xenonbyte.gestitre.domain.admin.vo.CredentialExpired;
import cm.xenonbyte.gestitre.domain.admin.vo.PermissionId;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.City;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.common.vo.Country;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import cm.xenonbyte.gestitre.domain.common.vo.ZipCode;
import cm.xenonbyte.gestitre.domain.company.CompanyDomainService;
import cm.xenonbyte.gestitre.domain.company.CompanyMessageInMemoryPublisher;
import cm.xenonbyte.gestitre.domain.company.addapter.inmemory.CertificateTemplateInMemoryRepository;
import cm.xenonbyte.gestitre.domain.company.addapter.inmemory.CompanyInMemoryRepository;
import cm.xenonbyte.gestitre.domain.company.addapter.inmemory.TenantInMemoryMessagePublisher;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CompanyRepository;
import cm.xenonbyte.gestitre.domain.company.vo.Activity;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyManagerName;
import cm.xenonbyte.gestitre.domain.company.vo.LegalForm;
import cm.xenonbyte.gestitre.domain.company.vo.Licence;
import cm.xenonbyte.gestitre.domain.company.vo.address.Address;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Contact;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantDomainService;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.admin.RoleNotFoundException.ROLE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.admin.UserEmailConflictException.USER_EMAIL_CONFLICT;
import static cm.xenonbyte.gestitre.domain.admin.UserLoginAccountDisableUnAuthorizedException.USER_LOGIN_ACCOUNT_DISABLE_UN_AUTHORIZED;
import static cm.xenonbyte.gestitre.domain.admin.UserLoginAccountExpiredUnAuthorizedException.USER_LOGIN_ACCOUNT_EXPIRED_UN_AUTHORIZED;
import static cm.xenonbyte.gestitre.domain.admin.UserLoginAccountLockedUnAuthorizedException.USER_LOGIN_ACCOUNT_LOCKED_UN_AUTHORIZED;
import static cm.xenonbyte.gestitre.domain.admin.UserLoginCredentialExpiredUnAuthorizedException.USER_LOGIN_CREDENTIAL_EXPIRED_UN_AUTHORIZED;
import static cm.xenonbyte.gestitre.domain.admin.UserLoginEmailUnAuthorizedException.USER_LOGIN_EMAIL_UN_AUTHORIZED;
import static cm.xenonbyte.gestitre.domain.admin.UserLoginPasswordUnAuthorizedException.USER_LOGIN_PASSWORD_UN_AUTHORIZED;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException.COMPANY_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
class UserDomainServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private CompanyId companyId;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        userRepository = new UserInMemoryRepository();
        RoleRepository roleRepository = new RoleInMemoryRepository();
        TenantRepository tenantRepository = new TenantInMemoryRepository();
        TenantMessagePublisher tenantInMemoryMessagePublisher = new TenantInMemoryMessagePublisher();
        TenantService tenantService = new TenantDomainService(tenantRepository, tenantInMemoryMessagePublisher);
        CompanyRepository companyRepository = new CompanyInMemoryRepository();
        CompanyService companyService = new CompanyDomainService(
                companyRepository,
                new CertificateTemplateInMemoryRepository(),
                new CompanyMessageInMemoryPublisher()
        );
        PasswordEncryptProvider passwordEncryptProvider = new PasswordInMemoryProvider();
        UserMessagePublisher userMessagePublisher = new UserMessageInMemoryPublisher();

        TenantId tenantId = new TenantId(UUID.fromString("0193127b-1508-7da5-95fe-203d58fa0a97"));

        TokenProvider tokenProvider = new TokenInMemoryProvider();

        userService = new UserDomainService(
                userRepository,
                roleRepository,
                tenantService,
                companyService,
                passwordEncryptProvider,
                userMessagePublisher,
                tokenProvider
        );

        companyId = new CompanyId(UUID.fromString("01935b63-7185-7796-b65c-2840e57ab857"));

        companyRepository.create(
                Company.builder()
                        .id(companyId)
                        .companyName(CompanyName.of(Text.of("Company name 0")))
                        .companyManagerName(CompanyManagerName.of(Text.of("Company manager name")))
                        .licence(Licence.MONTH_12)
                        .legalForm(LegalForm.SA)

                        .address(
                                Address.of(
                                        ZipCode.of(Text.of("360")),
                                        City.of(Text.of("City0")),
                                        Country.of(Text.of("Country0"))
                                )
                        )
                        .contact(
                                Contact.builder()
                                        .phone(Phone.of(Text.of("698 254 780")))
                                        .name(Name.of(Text.of("Name0")))
                                        .email(Email.of( Text.of("email0@email.test")))
                                        .build()
                        )
                        .activity(Activity.of(Text.of("activity0")))
                        .active(Active.with(true))
                        .build()
        );

        tenantRepository.create(
                Tenant.builder()
                        .id(tenantId)
                        .name(Name.of(Text.of("Company name 0")))
                        .build()
        );

        Role role = Role.builder()
                .id(new RoleId(UUID.fromString("0193127b-3f4c-710e-8680-f97ba96bd7af")))
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
                .build();
        roleRepository.create(role);

        roles = Set.of(role);

        userRepository.create(
            User.builder()
                .name(Name.of(Text.of("Second User")))
                .email(Email.of(Text.of("emailsecond@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .tenantId(tenantId)
                .roles(Set.of(role))
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
                .roles(roles)
                .companyId(companyId)
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
                .roles(Set.of(
                        Role.builder()
                                .id(new RoleId(UUID.fromString("01936fd1-0b90-7b2e-a4cd-3c7324ddeca7")))
                                .permissions(Set.of(
                                        Permission.builder()
                                                .id(new PermissionId(UUID.fromString("01936fd1-34a2-74b2-bd22-7208b20f8c17")))
                                                .name(Name.of(Text.of("create:role")))
                                                .build()
                                ))
                                .build()
                ))
                .companyId(companyId)
                .build();
        //Act + Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage(ROLE_NOT_FOUND);
    }

    @Test
    void should_fail_when_create_user_with_invalid_company_id() {
        //Given
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937041-908c-74cb-a0ce-c73685736175")))
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("test@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .build();
        //Act + Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(CompanyNotFoundException.class)
                .hasMessage(COMPANY_NOT_FOUND);
    }


    @Test
    void should_fail_when_create_user_with_duplicate_email() {
        //Given
        User user = User.builder()
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("emailsecond@gmail.com")))
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(companyId)
                .build();
        //Act + Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(UserEmailConflictException.class)
                .hasMessage(USER_EMAIL_CONFLICT);
    }

    @Test
    void should_create_token_when_login_with_valid_credentials() {
        //Given
        Email email = Email.of(Text.of("test@gmail.com"));
        Password password = Password.of(Text.of("test123"));
        User user = User.builder()
            .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
            .name(Name.of(Text.of("First User")))
            .email(email)
            .password(password)
            .confirmPassword(password)
            .roles(roles)
            .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
            .accountEnabled(AccountEnabled.with(true))
            .accountExpired(AccountExpired.with(false))
            .accountLocked(AccountLocked.with(false))
            .credentialExpired(CredentialExpired.with(false))
            .build();
        userRepository.create(user);

        //Act
        User actual = userService.login(email, password);

        //Then
        assertThat(actual).isNotNull();
    }

    @Test
    void should_fail_when_login_with_invalid_email() {
        Email email = Email.of(Text.of("test1@gmail.com"));
        Password password = Password.of(Text.of("test123"));
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
                .name(Name.of(Text.of("First User")))
                .email(Email.of(Text.of("test@gmail.com")))
                .password(password)
                .confirmPassword(password)
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .accountEnabled(AccountEnabled.with(true))
                .accountExpired(AccountExpired.with(false))
                .accountLocked(AccountLocked.with(false))
                .credentialExpired(CredentialExpired.with(false))
                .build();
        userRepository.create(user);
        //Act + Then
        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(UserLoginEmailUnAuthorizedException.class)
                .hasMessage(USER_LOGIN_EMAIL_UN_AUTHORIZED);
    }

    @Test
    void should_fail_when_login_with_invalid_password() {
        Email email = Email.of(Text.of("test1@gmail.com"));
        Password password = Password.of(Text.of("test123!"));
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
                .name(Name.of(Text.of("First User")))
                .email(email)
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .accountEnabled(AccountEnabled.with(true))
                .accountExpired(AccountExpired.with(false))
                .accountLocked(AccountLocked.with(false))
                .credentialExpired(CredentialExpired.with(false))
                .build();
        userRepository.create(user);
        //Act + Then
        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(UserLoginPasswordUnAuthorizedException.class)
                .hasMessage(USER_LOGIN_PASSWORD_UN_AUTHORIZED);
    }

    @Test
    void should_fail_when_login_with_non_enable_account() {
        Email email = Email.of(Text.of("test1@gmail.com"));
        Password password = Password.of(Text.of("test123"));
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
                .name(Name.of(Text.of("First User")))
                .email(email)
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .accountEnabled(AccountEnabled.with(false))
                .accountExpired(AccountExpired.with(false))
                .accountLocked(AccountLocked.with(false))
                .credentialExpired(CredentialExpired.with(false))
                .build();
        userRepository.create(user);
        //Act + Then
        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(UserLoginAccountDisableUnAuthorizedException.class)
                .hasMessage(USER_LOGIN_ACCOUNT_DISABLE_UN_AUTHORIZED);
    }

    @Test
    void should_fail_when_login_with_expired_account() {
        Email email = Email.of(Text.of("test1@gmail.com"));
        Password password = Password.of(Text.of("test123"));
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
                .name(Name.of(Text.of("First User")))
                .email(email)
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .accountEnabled(AccountEnabled.with(true))
                .accountExpired(AccountExpired.with(true))
                .accountLocked(AccountLocked.with(false))
                .credentialExpired(CredentialExpired.with(false))
                .build();
        userRepository.create(user);
        //Act + Then
        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(UserLoginAccountExpiredUnAuthorizedException.class)
                .hasMessage(USER_LOGIN_ACCOUNT_EXPIRED_UN_AUTHORIZED);
    }

    @Test
    void should_fail_when_login_with_credential_expired() {
        Email email = Email.of(Text.of("test1@gmail.com"));
        Password password = Password.of(Text.of("test123"));
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
                .name(Name.of(Text.of("First User")))
                .email(email)
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .accountEnabled(AccountEnabled.with(true))
                .accountExpired(AccountExpired.with(false))
                .accountLocked(AccountLocked.with(false))
                .credentialExpired(CredentialExpired.with(true))
                .build();
        userRepository.create(user);
        //Act + Then
        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(UserLoginCredentialExpiredUnAuthorizedException.class)
                .hasMessage(USER_LOGIN_CREDENTIAL_EXPIRED_UN_AUTHORIZED);
    }

    @Test
    void should_fail_when_login_with_account_locked() {
        Email email = Email.of(Text.of("test1@gmail.com"));
        Password password = Password.of(Text.of("test123"));
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937040-0104-7f64-a292-29d12581d41d")))
                .name(Name.of(Text.of("First User")))
                .email(email)
                .password(Password.of(Text.of("test123")))
                .confirmPassword(Password.of(Text.of("test123")))
                .roles(roles)
                .companyId(new CompanyId(UUID.fromString("019353d5-b63c-7874-9d44-22622626500e")))
                .accountEnabled(AccountEnabled.with(true))
                .accountExpired(AccountExpired.with(false))
                .accountLocked(AccountLocked.with(true))
                .credentialExpired(CredentialExpired.with(false))
                .build();
        userRepository.create(user);
        //Act + Then
        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(UserLoginAccountLockedUnAuthorizedException.class)
                .hasMessage(USER_LOGIN_ACCOUNT_LOCKED_UN_AUTHORIZED);
    }
}
