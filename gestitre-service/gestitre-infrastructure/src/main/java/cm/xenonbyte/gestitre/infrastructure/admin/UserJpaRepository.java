package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.infrastructure.common.TenantPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@ApplicationScoped
public final class UserJpaRepository implements TenantPanacheRepository<UserJpa> {

    public Boolean existsByEmail(String email) {
        return find("email", email).count() > 0;
    }
}
