package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class UserJpaRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;

    public UserJpaRepositoryAdapter(
            @Nonnull UserJpaRepository userJpaRepository,
            @Nonnull UserJpaMapper userJpaMapper
    ) {
        this.userJpaRepository = Objects.requireNonNull(userJpaRepository);
        this.userJpaMapper = Objects.requireNonNull(userJpaMapper);
    }

    @Override
    public Boolean existsByEmail(@Nonnull Email email) {
        return userJpaRepository.existsByEmail(email.text().value());
    }

    @Override
    @Transactional
    public User create(@Nonnull User user) {
        userJpaRepository.persist(userJpaMapper.toUserJpa(user));
        return user;
    }

    @Override
    public Optional<User> findByEmail(@Nonnull Email email) {
        return userJpaRepository.findByEmail(email.text().value())
                .map(userJpaMapper::toUser);
    }

    @Override
    public Optional<User> findById(@Nonnull UserId userId) {
        return userJpaRepository.findByIdOptional(userId.getValue())
                .map(userJpaMapper::toUser);
    }

    @Override
    @Transactional
    public User update(@Nonnull UserId userId, @Nonnull User newUser) {
        UserJpa oldUserJpa = userJpaRepository.findById(userId.getValue());
        UserJpa newUserJpa = userJpaMapper.toUserJpa(newUser);
        userJpaMapper.copyNewToOldUserJpa(newUserJpa, oldUserJpa);
        return userJpaMapper.toUser(oldUserJpa);
    }
}
