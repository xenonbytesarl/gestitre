package cm.xenonbyte.gestitre.infrastructure.shareholder;

import cm.xenonbyte.gestitre.infrastructure.common.TenantPanacheRepository;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.TenantInterceptorBinding;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@ApplicationScoped
@TenantInterceptorBinding
 public final class ShareHolderJpaRepository implements TenantPanacheRepository<ShareHolderJpa> {
    public Boolean existsByAccountNumber(String accountNumber) {
        return find("accountNumber", accountNumber).count() > 0;
    }

    public Optional<ShareHolderJpa> findByAccountNumber(String accountNumber) {
        return find("accountNumber", accountNumber).stream().findFirst();
    }

    public Boolean existsByBankAccountNumber(String bankAccountNumber) {
        return find("bankAccountNumber", bankAccountNumber).count() > 0;
    }

    public Optional<ShareHolderJpa> findByBankAccountNumber(String bankAccountNumber) {
        return find("bankAccountNumber", bankAccountNumber).stream().findFirst();
    }

    public Boolean existsByPhone(String phone) {
        return find("phone", phone).count() > 0;
    }

    public Optional<ShareHolderJpa> findByPhone(String phone) {
        return find("phone", phone).stream().findFirst();
    }

    public Boolean existsByEmail(String email) {
        return find("email", email).count() > 0;
    }

    public Optional<ShareHolderJpa> findByEmail(String email) {
        return find("email", email).stream().findFirst();
    }
}
