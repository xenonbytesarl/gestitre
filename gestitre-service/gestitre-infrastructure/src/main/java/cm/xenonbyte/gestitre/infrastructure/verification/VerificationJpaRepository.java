package cm.xenonbyte.gestitre.infrastructure.verification;

import cm.xenonbyte.gestitre.infrastructure.admin.UserJpa;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.TenantPanacheInterceptor;
import cm.xenonbyte.gestitre.infrastructure.notification.MailServerJpa;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@ApplicationScoped
@TenantPanacheInterceptor
public final class VerificationJpaRepository implements PanacheRepositoryBase<VerificationJpa, UUID> {
    public Optional<VerificationJpa> findByUserAndStatus(UserJpa userJpa, VerificationStatusJpa verificationStatusJpa) {
        return find(
                "userJpa.id = :userId and status = :status",
                Parameters
                        .with("userId", userJpa.getId())
                        .and("status", verificationStatusJpa)
        ).firstResultOptional();
    }

    public Optional<VerificationJpa> findByCodeAndEmail(String code, String email) {
        return find(
                "code = :code and email = :email",
                Parameters.with("code", code).and("email", email)
        ).firstResultOptional();
    }

    public Optional<VerificationJpa> findByCode(String code) {
        return find("code", code).firstResultOptional();
    }

    public Optional<VerificationJpa> findByMailServerAndStatus(MailServerJpa mailServerJpa, VerificationStatusJpa verificationStatusJpa) {
        return find(
                "mailServerJpa.id = :mailServerId and status = :status",
                Parameters
                        .with("mailServerId", mailServerJpa.getId())
                        .and("status", verificationStatusJpa)
        ).firstResultOptional();
    }
}
