package cm.xenonbyte.gestitre.infrastructure.admin;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@ApplicationScoped
public final class UserJpaRepository implements PanacheRepositoryBase<UserJpa, UUID> {

    public Boolean existsByEmail(String email) {
        return find("email", email).count() > 0;
    }

    public Optional<UserJpa> findByEmail(String email) {
        return find("email", email).stream().findFirst();
    }
}
