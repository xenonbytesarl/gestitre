package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Objects;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.SHARE_HOLDER_API_PATH;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
@Slf4j
@Path(SHARE_HOLDER_API_PATH)
public final class ShareHolderResource {

    private static final String SHARE_HOLDER_CREATED_SUCCESSFULLY = "ShareHolderResource.1";
    private static final String SHARE_HOLDER_FINDS_SUCCESSFULLY = "ShareHolderResource.2";
    private final ShareHolderApplicationAdapter shareHolderApplicationAdapter;


    public ShareHolderResource(ShareHolderApplicationAdapter shareHolderApplicationAdapter) {
        this.shareHolderApplicationAdapter = Objects.requireNonNull(shareHolderApplicationAdapter);

    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:shareholder"})
    public Response createShareHolder(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid CreateShareHolderViewRequest createShareHolderViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(SHARE_HOLDER_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, shareHolderApplicationAdapter.createShareHolder(createShareHolderViewRequest)))
                                .build()
                )
                .build();
    }

    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:shareholder"})
    public Response findAllShareHolder(
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
                                .message(getMessage(SHARE_HOLDER_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, shareHolderApplicationAdapter.findShareHolders(page, size, field, direction, keyword)))
                )
                .build();
    }
}
