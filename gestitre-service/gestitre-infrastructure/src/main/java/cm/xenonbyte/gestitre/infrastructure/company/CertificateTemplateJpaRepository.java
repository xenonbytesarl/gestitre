package cm.xenonbyte.gestitre.infrastructure.company;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;



import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@ApplicationScoped
public final class CertificateTemplateJpaRepository implements PanacheRepositoryBase<CertificateTemplateJpa, UUID> {

    public CertificateTemplateJpa findByName(String name) {
        return find("name", name).firstResult();
    }
}
