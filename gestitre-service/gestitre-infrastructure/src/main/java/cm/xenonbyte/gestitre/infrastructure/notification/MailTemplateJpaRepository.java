package cm.xenonbyte.gestitre.infrastructure.notification;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@ApplicationScoped
public class MailTemplateJpaRepository implements PanacheRepositoryBase<MailTemplateJpa, UUID> {
    public Optional<MailTemplateJpa> findByType(MailTemplateTypeJpa type) {
        return find("type", type).firstResultOptional();
    }

    public Boolean existsByType(MailTemplateTypeJpa type) {
        return find("type", type).count() > 0;
    }

    public Boolean existsByName(String name) {
        return find("name", name).count() > 0;
    }

    public Optional<MailTemplateJpa> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
}
