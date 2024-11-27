package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
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
import cm.xenonbyte.gestitre.domain.company.vo.address.City;
import cm.xenonbyte.gestitre.domain.company.vo.address.Country;
import cm.xenonbyte.gestitre.domain.company.vo.address.ZipCode;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Contact;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
import cm.xenonbyte.gestitre.domain.admin.adapter.PasswordInMemoryProvider;
import cm.xenonbyte.gestitre.domain.admin.adapter.RoleInMemoryRepository;
import cm.xenonbyte.gestitre.domain.admin.adapter.TenantInMemoryRepository;
import cm.xenonbyte.gestitre.domain.admin.adapter.UserInMemoryRepository;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.vo.Password;
import cm.xenonbyte.gestitre.domain.admin.vo.PermissionId;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantDomainService;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.message.TenantMessagePublisher;
import cm.xenonbyte.gestitre.domain.tenant.ports.secondary.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException.COMPANY_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.admin.RoleNotFoundException.ROLE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.admin.UserEmailConflictException.USER_EMAIL_CONFLICT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
class UserDomainServiceTest {

    private UserService userService;
    private CompanyId companyId;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        UserRepository userRepository = new UserInMemoryRepository();
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

        userService = new UserDomainService(
                userRepository,
                roleRepository,
                tenantService,
                companyService,
                passwordEncryptProvider,
                userMessagePublisher
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
}
