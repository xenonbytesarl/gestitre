package cm.xenonbyte.gestitre.infrastructure.common;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public interface TenantPanacheRepository<T> extends PanacheRepositoryBase<T, UUID> {
}
