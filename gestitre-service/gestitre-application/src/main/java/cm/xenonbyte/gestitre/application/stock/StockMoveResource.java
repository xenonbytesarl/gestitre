package cm.xenonbyte.gestitre.application.stock;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.stock.dto.CreateStockMoveViewRequest;
import cm.xenonbyte.gestitre.application.stock.dto.UpdateStockMoveViewRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.STOCK_MOVE_API_PATH;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;
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
    private static final String STOCK_MOVE_FINDS_SUCCESSFULLY = "StockMoveResource.2";
    private static final String STOCK_MOVE_FIND_SUCCESSFULLY = "StockMoveResource.3";
    private static final String STOCK_MOVE_UPDATED_SUCCESSFULLY = "StockMoveResource.4";
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
            @RestForm("file") @NotNull(message = NOT_NULL) FileUpload file,
            @RestForm("createStockMoveViewRequest") @PartType(APPLICATION_JSON) @Valid CreateStockMoveViewRequest createStockMoveViewRequest
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

    @GET
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"search:stock:move"})
    public Response searchStockMoves(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("field") String field,
            @QueryParam("direction") String direction,
            @QueryParam("keyword") String keyword
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(STOCK_MOVE_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, stockMoveApplicationAdapter.searchStockMoves(page, size, field, direction, keyword)))
                )
                .build();
    }

    @GET
    @Path("/{stockMoveId}")
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:stock:move"})
    public Response findStockMoveById(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("stockMoveId") UUID stockMoveId
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(STOCK_MOVE_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, stockMoveApplicationAdapter.findStockMoveById(stockMoveId)))
                )
                .build();
    }

    @PUT
    @Path("/{stockMoveId}")
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"update:stock:move"})
    public Response updateStockMove(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("stockMoveId") UUID stockMoveId,
            @RestForm("file") FileUpload file,
            @RestForm("createStockMoveViewRequest") @PartType(APPLICATION_JSON) @Valid UpdateStockMoveViewRequest updateStockMoveViewRequest
    ) throws IOException {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(STOCK_MOVE_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, stockMoveApplicationAdapter.updateStockMove(stockMoveId, file, updateStockMoveViewRequest)))
                                .build()
                )
                .build();
    }
}
