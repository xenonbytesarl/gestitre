package cm.xenonbyte.gestitre.domain.admin.adapter;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.UserRepository;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import jakarta.annotation.Nonnull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class UserInMemoryRepository implements UserRepository {

    private Map<UserId, User> users = new LinkedHashMap<>();
    @Override
    public Boolean existsByEmail(@Nonnull Email email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().text().value().equalsIgnoreCase(email.text().value()));
    }

    @Override
    public User create(@Nonnull User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(@Nonnull Email email) {
        return users.values().stream().filter(user ->
                user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findById(@Nonnull UserId userId) {
        User user = users.get(userId);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public User update(@Nonnull UserId userId, @Nonnull User newUser) {
        users.replace(userId, newUser);
        return newUser;
    }
}
