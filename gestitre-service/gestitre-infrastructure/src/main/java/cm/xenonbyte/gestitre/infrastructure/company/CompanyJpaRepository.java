package cm.xenonbyte.gestitre.infrastructure.company;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@ApplicationScoped
public final class CompanyJpaRepository implements PanacheRepositoryBase<CompanyJpa, UUID> {
    public Boolean existsByCompanyName(String companyName) {
        return find("companyName", companyName).count() > 0;
    }

    public Boolean existsByEmail(String email) {
        return find("contactJpa.email", email).count() > 0;
    }

    public Boolean existsByPhone(String phone) {
        return find("replace(contactJpa.phone, ' ', '') = :phone", Parameters.with("phone", phone.replaceAll("\\s", ""))).count() > 0;
    }

    public Optional<CompanyJpa> findByPhone(String phone) {
         return find("replace(contactJpa.phone, ' ', '') = :phone", Parameters.with("phone", phone.replaceAll("\\s", ""))).firstResultOptional();
    }

    public Optional<CompanyJpa> findByEmail(String email) {
        return find("contactJpa.email", email).firstResultOptional();
    }

    public Optional<CompanyJpa> findCompanyName(String companyName) {
        return find("companyName", companyName).firstResultOptional();
    }
}
