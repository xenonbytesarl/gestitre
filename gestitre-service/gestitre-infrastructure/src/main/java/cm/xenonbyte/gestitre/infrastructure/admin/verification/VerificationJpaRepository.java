package cm.xenonbyte.gestitre.infrastructure.admin.verification;

import cm.xenonbyte.gestitre.infrastructure.admin.UserJpa;
import cm.xenonbyte.gestitre.infrastructure.common.TenantPanacheRepository;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.TenantPanacheInterceptor;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@ApplicationScoped
@TenantPanacheInterceptor
public final class VerificationJpaRepository implements TenantPanacheRepository<VerificationJpa> {
    public Optional<VerificationJpa> findByUserAndStatus(UserJpa userJpa, VerificationStatusJpa verificationStatusJpa) {
        return find(
                "userJpa.id = :userId and status = :status",
                Parameters
                        .with("userId", userJpa.getId())
                        .and("status", verificationStatusJpa)
        ).firstResultOptional();
    }
}
