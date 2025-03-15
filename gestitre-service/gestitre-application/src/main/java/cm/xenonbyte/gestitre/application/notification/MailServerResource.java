package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.notification.dto.ConfirmMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
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

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAIL_SERVER_API_PATH;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Path(MAIL_SERVER_API_PATH)
public final class MailServerResource {

    private static final String MAIL_SERVER_CREATED_SUCCESSFULLY = "MailServerResource.1";
    private static final String MAIL_SERVER_UPDATED_SUCCESSFULLY = "MailServerResource.2";
    private static final String MAIL_SERVER_FIND_SUCCESSFULLY = "MailServerResource.3";
    private static final String MAIL_SERVER_FINDS_SUCCESSFULLY = "MailServerResource.4";

    private final MailServerApplicationAdapter mailServerApplicationAdapter;

    public MailServerResource(MailServerApplicationAdapter mailServerApplicationAdapter) {
        this.mailServerApplicationAdapter = Objects.requireNonNull(mailServerApplicationAdapter);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:mail_server"})
    public Response createMailServer(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid CreateMailServerViewRequest createMailServerViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_SERVER_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailServerApplicationAdapter.createMailServer(createMailServerViewRequest)))
                                .build()
                )
                .build();
    }

    @PUT
    @Path("/{mailServerId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"update:mail_server"})
    public Response updateMailServer(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("mailServerId") UUID mailServerId,
            @Valid UpdateMailServerViewRequest updateMailServerViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_SERVER_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailServerApplicationAdapter.updateMailServer(mailServerId, updateMailServerViewRequest)))
                                .build()
                )
                .build();
    }

    @PUT
    @Path("/{mailServerId}/confirm")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"update:mail_server"})
    public Response confirmMailServer(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("mailServerId") UUID mailServerId,
            @Valid ConfirmMailServerViewRequest confirmMailServerViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_SERVER_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailServerApplicationAdapter.confirmMailServer(mailServerId, confirmMailServerViewRequest)))
                                .build()
                )
                .build();
    }

    @GET
    @Path("/{mailServerId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:mail_server"})
    public Response findMailServerById(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("mailServerId") UUID mailServerId
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_SERVER_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailServerApplicationAdapter.findMailServerById(mailServerId)))
                                .build()
                )
                .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:mail_server"})
    public Response searchMailServers(
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
                                .message(getMessage(MAIL_SERVER_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailServerApplicationAdapter.searchMailServers(page, size, field, direction, keyword)))
                )
                .build();
    }
}
