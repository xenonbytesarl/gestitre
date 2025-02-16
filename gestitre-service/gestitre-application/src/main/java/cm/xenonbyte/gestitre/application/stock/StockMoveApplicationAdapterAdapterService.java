package cm.xenonbyte.gestitre.application.stock;

import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.Image;
import cm.xenonbyte.gestitre.domain.common.vo.StorageLocation;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.file.port.primary.StorageManager;
import cm.xenonbyte.gestitre.domain.stock.entity.StockMove;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveCreatedEvent;
import cm.xenonbyte.gestitre.domain.stock.ports.primary.StockMoveService;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@ApplicationScoped
public final class StockMoveApplicationAdapterAdapterService implements StockMoveApplicationAdapter {

    private final StockMoveService stockMoveService;
    private final StockMoveViewMapper stockMoveViewMapper;
    private final StorageManager storageManager;
    @ConfigProperty(name = "storage.root.path", defaultValue = "")
    String storageRootPath;
    @ConfigProperty(name = "storage.root.stock.move.path.file", defaultValue = "")
    String storageRootStockMovePathFile;

    public StockMoveApplicationAdapterAdapterService(
            final StockMoveService stockMoveService,
            final StockMoveViewMapper stockMoveViewMapper,
            final StorageManager storageManager) {
        this.stockMoveService = Objects.requireNonNull(stockMoveService);
        this.stockMoveViewMapper = Objects.requireNonNull(stockMoveViewMapper);
        this.storageManager = Objects.requireNonNull(storageManager);
    }

    @Nonnull
    @Override
    @Transactional
    public CreateStockMoveViewResponse createStockMove(@Nonnull FileUpload file, @Nonnull CreateStockMoveViewRequest createStockMoveViewRequest) throws IOException {
        Image imageFile = file == null || file.fileName() == null
                ? null
                : createStockMoveViewRequest.getFilename() == null || createStockMoveViewRequest.getFilename().isEmpty()
                ?
                Image.with(Text.of(Objects.requireNonNull(file.fileName())), Files.newInputStream(file.filePath()))
                        .computeImageName(storageRootPath, storageRootStockMovePathFile)
                :  Image.with(Text.of(createStockMoveViewRequest.getFilename()), Files.newInputStream(file.filePath()));
        StockMoveCreatedEvent stockMoveCreatedEvent = stockMoveService.createStockMove(
                stockMoveViewMapper.toStockMove(createStockMoveViewRequest)
        );
        saveImageFile(imageFile);
        CreateStockMoveViewResponse createStockMoveViewResponse = stockMoveViewMapper.toCreateStockMoveViewResponse(
                stockMoveCreatedEvent.getStockMove()
        );
        GetEncodedFiles getEncodedFiles = getGetEncodedFiles(stockMoveCreatedEvent.getStockMove());
        createStockMoveViewResponse.setFileEncoded(getEncodedFiles.fileEncoded());
        createStockMoveViewResponse.setFileMimeType(getEncodedFiles.fileMimeType());
        return createStockMoveViewResponse;
    }

    private void saveImageFile(Image imageFile) {
        if(imageFile != null) {
            storageManager.store(imageFile, StorageLocation.of(Text.of(imageFile.name().value())));
        }
    }

    private GetEncodedFiles getGetEncodedFiles(StockMove stockMove) throws IOException {
        String logoEncoded = stockMove.getFilename() == null || stockMove.getFilename().text().value().isEmpty()? null: storageManager.fileToBase64(stockMove.getFilename().text().value());
        String logoMimeType = stockMove.getFilename() == null || stockMove.getFilename().text().value().isEmpty()? null: storageManager.mimeType(stockMove.getFilename().text().value());
        return new GetEncodedFiles(logoEncoded, logoMimeType);
    }
    private record GetEncodedFiles(String fileEncoded, String fileMimeType) {}
}
