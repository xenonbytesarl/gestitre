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

    public Boolean existsByRegistrationNumber(String registrationNumber) {
        return find("registrationNumber", registrationNumber).count() > 0;
    }

    public Boolean existsTaxNumber(String taxNumber) {
        return find("taxNumber", taxNumber).count() > 0;
    }

    public Boolean existsByIsinCode(String isinCode) {
        return find("isinCode", isinCode).count() > 0;
    }

    public Boolean existsByWebSiteUrl(String webSiteUrl) {
        return find("webSiteUrl", webSiteUrl).count() > 0;
    }

    public Optional<CompanyJpa> findByRegistrationNumber(String registrationNumber) {
        return find("registrationNumber", registrationNumber).firstResultOptional();
    }

    public Optional<CompanyJpa> findTaxNumber(String taxNumber) {
        return find("taxNumber", taxNumber).firstResultOptional();
    }

    public Optional<CompanyJpa> findByIsinCode(String isinCode) {
        return find("isinCode", isinCode).firstResultOptional();
    }

    public Optional<CompanyJpa> findByWebSiteUrl(String webSiteUrl) {
        return find("webSiteUrl", webSiteUrl).firstResultOptional();
    }

    public Boolean existsByFax(String fax) {
        return find("replace(contactJpa.fax, ' ', '') = :fax", Parameters.with("fax", fax.replaceAll("\\s", ""))).count() > 0;
    }

    public Optional<CompanyJpa> findByFax(String fax) {
        return find("replace(contactJpa.fax, ' ', '') = :fax", Parameters.with("fax", fax.replaceAll("\\s", ""))).firstResultOptional();
    }

    public Optional<CompanyJpa> findByTenantId(UUID tenantId) {
        return find("tenantId", tenantId).firstResultOptional();
    }
}
