package cm.xenonbyte.gestitre.application.stock;

import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.FindStockMovePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.SearchStockMovePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.stock.dto.UpdateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.UpdateStockMoveViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMoveApplicationAdapter {

    @Nonnull @Valid
    CreateStockMoveViewResponse createStockMove(@Nonnull FileUpload file, @Nonnull @Valid CreateStockMoveViewRequest createStockMoveViewRequest) throws IOException;

    @Nonnull SearchStockMovePageInfoViewResponse searchStockMoves(Integer page, Integer size, String field, String direction, String keyword);

    FindStockMovePageInfoViewResponse findStockMoveById(UUID stockMoveId);

    @Nonnull @Valid UpdateStockMoveViewResponse updateStockMove(UUID stockMoveId, FileUpload file, @Nonnull @Valid UpdateStockMoveViewRequest updateStockMoveViewRequest) throws IOException;
}
