package cm.xenonbyte.gestitre.infrastructure.admin;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@ApplicationScoped
public final class RoleJpaRepository implements PanacheRepositoryBase<RoleJpa, UUID> {
}
