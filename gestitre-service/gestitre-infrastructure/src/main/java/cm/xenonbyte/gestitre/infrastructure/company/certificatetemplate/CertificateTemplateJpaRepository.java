package cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@ApplicationScoped
public final class CertificateTemplateJpaRepository implements PanacheRepositoryBase<CertificateTemplateJpa, UUID> {

    public Boolean existsByName(String name) {
        return find("name", name).count() > 0;
    }

    public Optional<CertificateTemplateJpa> findByName(String name) {
        return find("lower(name) = :name", Parameters.with("name", name.toLowerCase())).firstResultOptional();
    }
}
