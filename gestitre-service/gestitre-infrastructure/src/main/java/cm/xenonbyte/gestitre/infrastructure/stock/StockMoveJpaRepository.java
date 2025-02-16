package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.infrastructure.common.TenantPanacheRepository;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.TenantInterceptorBinding;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@ApplicationScoped
@TenantInterceptorBinding
public final class StockMoveJpaRepository implements TenantPanacheRepository<StockMoveJpa> {

}
