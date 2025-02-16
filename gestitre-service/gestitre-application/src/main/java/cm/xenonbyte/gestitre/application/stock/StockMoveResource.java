package cm.xenonbyte.gestitre.application.stock;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.STOCK_MOVE_API_PATH;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@Slf4j
@Path(STOCK_MOVE_API_PATH)
public final class StockMoveResource {

    private static final String STOCK_MOVE_CREATED_SUCCESSFULLY = "StockMoveResource.1";
    private final StockMoveApplicationAdapter stockMoveApplicationAdapter;

    public StockMoveResource(StockMoveApplicationAdapter stockMoveApplicationAdapter) {
        this.stockMoveApplicationAdapter = Objects.requireNonNull(stockMoveApplicationAdapter);
    }

    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:stock:move"})
    public Response createStockMove(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @RestForm("file") FileUpload file,
            @RestForm @PartType(APPLICATION_JSON) @Valid CreateStockMoveViewRequest createStockMoveViewRequest
    ) throws IOException {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(STOCK_MOVE_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, stockMoveApplicationAdapter.createStockMove(file, createStockMoveViewRequest)))
                                .build()
                )
                .build();
    }
}
