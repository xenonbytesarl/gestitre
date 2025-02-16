package cm.xenonbyte.gestitre.application.stock;

import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public interface StockMoveApplicationAdapter {

    @Nonnull @Valid
    CreateStockMoveViewResponse createStockMove(@Nonnull FileUpload file, @Nonnull @Valid CreateStockMoveViewRequest createStockMoveViewRequest) throws IOException;

}
