package cm.xenonbyte.gestitre.domain.stock;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CompanyRepository;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveCreatedEvent;
import cm.xenonbyte.gestitre.domain.stock.ports.primary.StockMoveService;
import cm.xenonbyte.gestitre.domain.stock.ports.secondary.StockMoveRepository;
import cm.xenonbyte.gestitre.domain.stock.ports.secondary.message.StockMessagePublisher;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.stock.event.StockMoveEventType.STOCK_MOVE_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@DomainService
public final class StockMoveDomainService implements StockMoveService {

    public static final Logger LOGGER =  Logger.getLogger(StockMoveDomainService .class.getName());

    private final StockMoveRepository stockMoveRepository;
    private final StockMessagePublisher stockMoveMessagePublisher;
    private final CompanyRepository companyRepository;

    public StockMoveDomainService(
            final StockMoveRepository stockMoveRepository,
            final StockMessagePublisher stockMoveMessagePublisher,
            final CompanyRepository companyRepository) {
        this.stockMoveRepository = Objects.requireNonNull(stockMoveRepository);
        this.stockMoveMessagePublisher = Objects.requireNonNull(stockMoveMessagePublisher);
        this.companyRepository = Objects.requireNonNull(companyRepository);
    }

    @Nonnull
    @Override
    public StockMoveCreatedEvent createStockMove(@Nonnull StockMove stockMove) {
        stockMove.validateMandatoryFields();
        validateStockMove(stockMove);
        stockMove.initializeDefaults();
        stockMove = stockMoveRepository.create(stockMove);
        LOGGER.info("Stock move is created with id " + stockMove.getId().getValue());
        StockMoveCreatedEvent stockMoveCreatedEvent = new StockMoveCreatedEvent(stockMove, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        stockMoveMessagePublisher.publish(stockMoveCreatedEvent, STOCK_MOVE_CREATED);
        return stockMoveCreatedEvent;
    }

    private void validateStockMove(StockMove stockMove) {
        validateCompany(stockMove.getCompanyId());
    }

    private void validateCompany(CompanyId companyId) {
        if(Boolean.FALSE.equals(companyRepository.existsById(companyId))) {
            throw new CompanyNotFoundException(new String[] {companyId.getValue().toString()});
        }
    }

}
