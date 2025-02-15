package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserPasswordResetedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserUpdatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.message.publisher.UserMessagePublisher;
import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.admin.vo.UserEventType.USER_ACTIVATED;
import static cm.xenonbyte.gestitre.domain.admin.vo.UserEventType.USER_CREATED;
import static cm.xenonbyte.gestitre.domain.admin.vo.UserEventType.USER_PASSWORD_RESET;
import static cm.xenonbyte.gestitre.domain.admin.vo.UserEventType.USER_UPDATED;
import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.validatePageParameters;

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
    private final UserMessagePublisher userMessagePublisher;
    private final TokenProvider tokenProvider;

    public UserDomainService(
            @Nonnull UserRepository userRepository,
            @Nonnull RoleRepository roleRepository,
            @Nonnull TenantService tenantService,
            @Nonnull CompanyService companyService,
            @Nonnull PasswordEncryptProvider passwordEncryptProvider,
            @Nonnull UserMessagePublisher userMessagePublisher, TokenProvider tokenProvider) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.roleRepository = Objects.requireNonNull(roleRepository);
        this.tenantService = Objects.requireNonNull(tenantService);
        this.companyService = Objects.requireNonNull(companyService);
        this.passwordEncryptProvider = Objects.requireNonNull(passwordEncryptProvider);
        this.userMessagePublisher = Objects.requireNonNull(userMessagePublisher);
        this.tokenProvider = Objects.requireNonNull(tokenProvider);
    }

    @Nonnull
    @Override
    public UserCreatedEvent createUser(@Nonnull User user) {
        user.validateMandatoryFields();
        user.validatePasswords();
        user.validatePassword();
        validateUser(user);
        Tenant tenant = findTenant(user.getCompanyId());
        user.encryptPassword(passwordEncryptProvider.encrypt(user.getPassword()));
        user.initializeDefaults(tenant.getId());
        user = userRepository.create(user);
        LOGGER.info("User created with id " + user.getId().getValue());
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        userMessagePublisher.publish(userCreatedEvent, USER_CREATED);
        return userCreatedEvent;
    }

    @Nonnull
    @Override
    public UserUpdatedEvent updateUser(@Nonnull UserId userId, @Nonnull User newUser) {
        newUser.validateMandatoryFields();
        User oldUser = findUserById(userId);
        validateUser(newUser);
        newUser.assignEncryptedPassword(oldUser.getPassword());
        newUser.assignTenant(oldUser.getTenantId());
        newUser = userRepository.update(userId, newUser);
        LOGGER.info("User updated with id " + newUser.getId().getValue());
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent(newUser, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()).withZoneSameInstant(Timezone.getCurrentZoneId()));
        userMessagePublisher.publish(userUpdatedEvent, USER_UPDATED);
        return userUpdatedEvent;
    }

    @Nonnull
    @Override
    public User login(@Nonnull Email email, @Nonnull Password password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = loginGuard(optionalUser.get());
            if (Boolean.FALSE.equals(passwordEncryptProvider.checkCredentials(password, optionalUser.get().getPassword()))) {
                throw new UserLoginPasswordUnAuthorizedException();
            }
            return user;
        }
        throw new UserLoginEmailUnAuthorizedException();
    }

    private static User loginGuard(User user) {
        if(Boolean.TRUE.equals(user.isAccountDisabled())) {
            throw new UserLoginAccountDisableUnAuthorizedException();
        }
        if(Boolean.TRUE.equals(user.isCredentialExpired())) {
            throw new UserLoginCredentialExpiredUnAuthorizedException();
        }
        if(Boolean.TRUE.equals(user.isAccountExpired())) {
            throw new UserLoginAccountExpiredUnAuthorizedException();
        }
        if(Boolean.TRUE.equals(user.isAccountLocked())) {
            throw new UserLoginAccountLockedUnAuthorizedException();
        }
        return user;
    }

    @Nonnull
    @Override
    public Token generateToken(@Nonnull User user) {
        return tokenProvider.generateToken(user);
    }

    @Nonnull
    @Override
    public User activateUser(User user) {
        user.activate();
        user = userRepository.update(user.getId(), user);
        LOGGER.info("User with id " + user.getId().getValue() + " activated.");
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent(user, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        userMessagePublisher.publish(userUpdatedEvent, USER_ACTIVATED);
        return user;
    }


    @Override
    public void resetPassword(@Nonnull Password oldPassword, @Nonnull Password newPassword, @Nonnull User user) {
        if(Boolean.TRUE.equals(passwordEncryptProvider.checkCredentials(oldPassword, user.getPassword()))) {
            user.encryptPassword(passwordEncryptProvider.encrypt(newPassword));
            user = userRepository.update(user.getId(), user);
            LOGGER.info("Password of user with id " + user.getId().getValue() + " was reset.");
            UserPasswordResetedEvent userPasswordResetedEvent = new UserPasswordResetedEvent(user, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
            userMessagePublisher.publish(userPasswordResetedEvent, USER_PASSWORD_RESET);
        } else {
            throw new UserPasswordResetBadRequestException();
        }
    }

    @Nonnull
    @Override
    public User findUserByEmail(@Nonnull Email email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException(new String[] {email.text().value()})
        );
    }

    @Override
    public User findUserById(UserId userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(new String[] {userId.getValue().toString()})
        );
    }

    @Nonnull
    @Override
    public PageInfo<User> searchUsers(PageInfoPage pageInfoPage, PageInfoSize pageInfoSize, PageInfoField pageInfoField, PageInfoDirection pageInfoDirection, Keyword keyword) {
        validatePageParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        Assert.field("Keyword", keyword).notNull();
        return userRepository.search(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
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
