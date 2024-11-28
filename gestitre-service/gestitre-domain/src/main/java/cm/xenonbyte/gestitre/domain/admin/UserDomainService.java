package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.vo.Password;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.admin.vo.UserId;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.admin.vo.UserEventType.USER_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
@DomainService
public final class UserDomainService implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserDomainService.class.getName());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TenantService tenantService;
    private final CompanyService companyService;
    private final PasswordEncryptProvider passwordEncryptProvider;
    private final UserMessagePublisher userEventPublisher;
    private final TokenProvider tokenProvider;

    public UserDomainService(
            @Nonnull UserRepository userRepository,
            @Nonnull RoleRepository roleRepository,
            @Nonnull TenantService tenantService,
            @Nonnull CompanyService companyService,
            @Nonnull PasswordEncryptProvider passwordEncryptProvider,
            @Nonnull UserMessagePublisher userEventPublisher, TokenProvider tokenProvider) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.roleRepository = Objects.requireNonNull(roleRepository);
        this.tenantService = Objects.requireNonNull(tenantService);
        this.companyService = Objects.requireNonNull(companyService);
        this.passwordEncryptProvider = Objects.requireNonNull(passwordEncryptProvider);
        this.userEventPublisher = Objects.requireNonNull(userEventPublisher);
        this.tokenProvider = Objects.requireNonNull(tokenProvider);
    }

    @Nonnull
    @Override
    public UserCreatedEvent createUser(@Nonnull User user) {
        user.validateMandatoryFields();
        user.validatePassword();
        validateUser(user);
        Tenant tenant = findTenant(user.getCompanyId());
        user.encryptPassword(passwordEncryptProvider.encrypt(user.getPassword()));
        user.initializeDefaults(tenant.getId());
        userRepository.create(user);
        LOGGER.info("User created with id " + user.getId().getValue());
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user, ZonedDateTime.now());
        userEventPublisher.publish(userCreatedEvent, USER_CREATED);
        return userCreatedEvent;
    }

    @Nonnull
    @Override
    public Token login(@Nonnull Email email, @Nonnull Password password) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            if (Boolean.FALSE.equals(passwordEncryptProvider.checkCredentials(password, optionalUser.get().getPassword()))) {
                throw new UserPasswordUnAuthorizedException();
            }
            return tokenProvider.generateToken(optionalUser.get());
        }
        throw new UserEmailUnAuthorizedException();
    }

    private void validateUser(User user) {
        validateEmail(user.getId(), user.getEmail());
        validateCompanyId(user.getCompanyId());
        validateRoles(user.getRoles());
    }

    private void validateCompanyId(CompanyId companyId) {
        if(Boolean.FALSE.equals(companyService.existsById(companyId))) {
            throw new CompanyNotFoundException(new String[] {companyId.getValue().toString()});
        }
    }

    private void validateRoles(Set<Role> roles) {
        roles.forEach(role -> {
            if(Boolean.FALSE.equals(roleRepository.existsById(role.getId()))) {
                throw new RoleNotFoundException(new String[] {role.getId().getValue().toString()});
            }
        });

    }

    private Tenant findTenant(CompanyId companyId) {

        Company company = companyService.findCompanyById(companyId);

        return tenantService.findByName(Name.of(company.getCompanyName().text()));
    }

    private void validateEmail(UserId userId, Email email) {
        if(userId == null && userRepository.existsByEmail(email)) {
            throw new UserEmailConflictException(new String[]{email.text().value()});
        }
    }
}
