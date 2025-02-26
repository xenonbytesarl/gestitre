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
    private final StockMoveLineJpaRepository stockMoveLineJpaRepository;
    private final StockMoveJpaMapper stockMoveJpaMapper;

    public StockMoveJpaRepositoryAdapter(
            final StockMoveJpaRepository stockMoveJpaRepository,
            final StockMoveLineJpaRepository stockMoveLineJpaRepository,
            final StockMoveJpaMapper stockMoveJpaMapper) {
        this.stockMoveJpaRepository = Objects.requireNonNull(stockMoveJpaRepository);
        this.stockMoveLineJpaRepository = Objects.requireNonNull(stockMoveLineJpaRepository);
        this.stockMoveJpaMapper = Objects.requireNonNull(stockMoveJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public StockMove create(@Nonnull StockMove stockMove) {
        StockMoveJpa stockMoveJpa = stockMoveJpaMapper.toStockMoveJpa(stockMove);
        stockMoveJpaRepository.persistAndFlush(stockMoveJpa);
        stockMoveLineJpaRepository.persist(stockMoveJpa.getMoveLinesJpa());
        return stockMoveJpaMapper.toStockMove(
                stockMoveJpaRepository.findById(stockMove.getId().getValue())
        );
    }
}
