package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.infrastructure.common.TenantPanacheRepository;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.TenantInterceptorBinding;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@ApplicationScoped
@TenantInterceptorBinding
public final class UserJpaRepository implements TenantPanacheRepository<UserJpa> {

    public Boolean existsByEmail(String email) {
        return find("email", email).count() > 0;
    }

    public Optional<UserJpa> findByEmail(String email) {
        return find("email", email).stream().findFirst();
    }
}
