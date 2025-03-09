package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.ports.secondary.StockMoveRepository;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveId;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@ApplicationScoped
public final class StockMoveJpaRepositoryAdapter implements StockMoveRepository {

    private static final String STOCK_SEARCH_BY_KEYWORD_QUERY = "select sm from StockMoveJpa sm where lower(concat(sm.reference, '', sm.companyName, '', sm.isinCode, '', sm.natureJpa, '', sm.typeJpa, '', coalesce(sm.observation, ''))) like lower(?1) order by sm.";


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

    @Nonnull
    @Override
    public PageInfo<StockMove> search(
            @Nonnull PageInfoPage page,
            @Nonnull PageInfoSize size,
            @Nonnull PageInfoField field,
            @Nonnull PageInfoDirection direction,
            @Nonnull Keyword keyword) {
        PanacheQuery<StockMoveJpa> queryResult = stockMoveJpaRepository.find(
                STOCK_SEARCH_BY_KEYWORD_QUERY + PageInfo.computeOderBy(field, direction), keyword.toLikeKeyword());
        PanacheQuery<StockMoveJpa> stockMovePageQueryResult =
                queryResult.page(Page.of(page.value(), size.value()));
        return new PageInfo<>(
                !stockMovePageQueryResult.hasPreviousPage(),
                !stockMovePageQueryResult.hasNextPage(),
                size.value(),
                stockMovePageQueryResult.count(),
                stockMovePageQueryResult.pageCount(),
                stockMovePageQueryResult
                        .list()
                        .stream()
                        .map(stockMoveJpaMapper::toStockMove)
                        .toList()
        );
    }

    @Override
    public Optional<StockMove> findById(@Nonnull StockMoveId stockMoveId) {
        return stockMoveJpaRepository.findByIdOptional(stockMoveId.getValue())
                .map(stockMoveJpaMapper::toStockMove);
    }

    @Nonnull
    @Override
    @Transactional
    public StockMove update(@Nonnull StockMoveId stockMoveId, @Nonnull StockMove newStockMove) {
        StockMoveJpa oldStockMoveJpa = stockMoveJpaRepository.findById(stockMoveId.getValue());
        StockMoveJpa newStockMoveJpa = stockMoveJpaMapper.toStockMoveJpa(newStockMove);
        stockMoveJpaMapper.copyNewToOldStockMoveJpa(newStockMoveJpa, oldStockMoveJpa);
        return stockMoveJpaMapper.toStockMove(oldStockMoveJpa);
    }
}
