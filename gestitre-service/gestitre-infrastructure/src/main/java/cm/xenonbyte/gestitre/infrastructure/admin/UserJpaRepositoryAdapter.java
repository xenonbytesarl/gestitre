package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.computeOderBy;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class UserJpaRepositoryAdapter implements UserRepository {

    private static final String USER_SEARCH_BY_KEYWORD_QUERY =
            "select u from UserJpa u where lower(concat(u.name, '', u.email, '', u.companyJpa.companyName)) like lower(?1) order by u.";

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
        return userJpaMapper.toUser(
                userJpaRepository.findById(user.getId().getValue())
        );
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

    @Override
    public PageInfo<User> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword) {
        PanacheQuery<UserJpa> queryResult =
                userJpaRepository.find(USER_SEARCH_BY_KEYWORD_QUERY + computeOderBy(pageInfoField, pageInfoDirection), keyword.toLikeKeyword());
        PanacheQuery<UserJpa> userPageQueryResult = queryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));
        return new PageInfo<>(
                !userPageQueryResult.hasPreviousPage(),
                !userPageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                userPageQueryResult.count(),
                userPageQueryResult.pageCount(),
                userPageQueryResult
                        .list()
                        .stream()
                        .map(userJpaMapper::toUser)
                        .toList()
        );
    }
}
