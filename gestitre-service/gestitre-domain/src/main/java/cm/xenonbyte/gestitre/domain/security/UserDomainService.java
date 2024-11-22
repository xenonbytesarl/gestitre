package cm.xenonbyte.gestitre.domain.security;

import cm.xenonbyte.gestitre.domain.common.entity.Tenant;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.security.ports.primary.PasswordEncryptService;
import cm.xenonbyte.gestitre.domain.security.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.RoleRepository;
import cm.xenonbyte.gestitre.domain.common.ports.secondary.TenantRepository;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.security.vo.RoleId;
import cm.xenonbyte.gestitre.domain.security.vo.UserId;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class UserDomainService implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserDomainService.class.getName());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncryptService passwordEncryptService;

    public UserDomainService(
            @Nonnull UserRepository userRepository,
            @Nonnull RoleRepository roleRepository,
            @Nonnull TenantRepository tenantRepository,
            @Nonnull PasswordEncryptService passwordEncryptService) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.roleRepository = Objects.requireNonNull(roleRepository);
        this.tenantRepository = Objects.requireNonNull(tenantRepository);
        this.passwordEncryptService = Objects.requireNonNull(passwordEncryptService);
    }

    @Nonnull
    @Override
    public UserCreatedEvent createUser(@Nonnull User user) throws Exception {
        user.validateMandatoryFields();
        user.validatePassword();
        validateUser(user);
        Tenant tenant = findTenant(TenantContext.current());
        user.encryptPassword(passwordEncryptService.encrypt(user.getPassword()));
        user.initializeDefaults(tenant.getId());
        userRepository.create(user);
        LOGGER.info("User created with id " + user.getId().getValue());
        //TODO fire event to audit manager
        return new UserCreatedEvent(user, ZonedDateTime.now());
    }

    private void validateUser(User user) {
        validateEmail(user.getId(), user.getEmail());
        validateRoleId(user.getRoleId());
    }

    private void validateRoleId(RoleId roleId) {
        if(!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException(new String[] {roleId.getValue().toString()});
        }
    }

    private Tenant findTenant(Name name) {
        if(name == null) {
            throw new TenantNameBadException();
        }
        return tenantRepository.findByName(name).orElseThrow(
                () -> new TenantNameNotFoundException(new String[]{name.text().value()})
        );
    }

    private void validateEmail(UserId userId, Email email) {
        if(userId == null && userRepository.existsByEmail(email)) {
            throw new UserEmailConflictException(new String[]{email.text().value()});
        }
    }
}
