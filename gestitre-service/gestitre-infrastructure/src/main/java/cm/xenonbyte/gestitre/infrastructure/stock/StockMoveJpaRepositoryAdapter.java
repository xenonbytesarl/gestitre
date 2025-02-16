package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.ports.secondary.StockMoveRepository;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@ApplicationScoped
public final class StockMoveJpaRepositoryAdapter implements StockMoveRepository {

    private final StockMoveJpaRepository stockMoveJpaRepository;
    private final StockMoveJpaMapper stockMoveJpaMapper;

    public StockMoveJpaRepositoryAdapter(
            final StockMoveJpaRepository stockMoveJpaRepository,
            final StockMoveJpaMapper stockMoveJpaMapper) {
        this.stockMoveJpaRepository = Objects.requireNonNull(stockMoveJpaRepository);
        this.stockMoveJpaMapper = Objects.requireNonNull(stockMoveJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public StockMove create(@Nonnull StockMove stockMove) {
        stockMoveJpaRepository.persist(
                stockMoveJpaMapper.toStockMoveJpa(stockMove)
        );
        return stockMoveJpaMapper.toStockMove(
                stockMoveJpaRepository.findById(stockMove.getId().getValue())
        );
    }
}
