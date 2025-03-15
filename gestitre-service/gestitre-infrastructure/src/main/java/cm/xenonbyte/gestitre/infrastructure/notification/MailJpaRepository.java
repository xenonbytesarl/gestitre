package cm.xenonbyte.gestitre.infrastructure.notification;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@ApplicationScoped
public class MailJpaRepository implements PanacheRepositoryBase<MailJpa, UUID> {
    public List<MailJpa> findByState(MailStateJpa state) {
        return find("state", state).stream().toList();
    }
}
