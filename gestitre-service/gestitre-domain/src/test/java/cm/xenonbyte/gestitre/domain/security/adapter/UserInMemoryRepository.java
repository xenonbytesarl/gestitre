package cm.xenonbyte.gestitre.domain.security.adapter;

import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.security.User;
import cm.xenonbyte.gestitre.domain.security.vo.UserId;
import cm.xenonbyte.gestitre.domain.security.ports.secondary.UserRepository;
import jakarta.annotation.Nonnull;

import java.util.LinkedHashMap;
import java.util.Map;

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
}
