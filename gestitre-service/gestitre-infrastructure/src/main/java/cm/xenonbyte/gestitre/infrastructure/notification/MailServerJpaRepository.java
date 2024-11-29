package cm.xenonbyte.gestitre.infrastructure.notification;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@ApplicationScoped
public final class MailServerJpaRepository implements PanacheRepositoryBase<MailServerJpa, UUID> {
    public Boolean existsByName(String name) {
        return find("name", name).count() > 0;
    }
}
