package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewRequest;
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
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAIL_TEMPLATE_API_PATH;
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
@Path(MAIL_TEMPLATE_API_PATH)
public final class MailTemplateResource {

    private static final String MAIL_TEMPLATE_CREATED_SUCCESSFULLY = "MailTemplateResource.1";
    private static final String MAIL_TEMPLATE_UPDATED_SUCCESSFULLY = "MailTemplateResource.2";
    private static final String MAIL_TEMPLATE_FIND_SUCCESSFULLY = "MailTemplateResource.3";
    private static final String MAIL_TEMPLATE_FINDS_SUCCESSFULLY = "MailTemplateResource.4";

    private final MailTemplateApplicationAdapter mailTemplateApplicationAdapter;

    public MailTemplateResource(MailTemplateApplicationAdapter mailTemplateApplicationAdapter) {
        this.mailTemplateApplicationAdapter = Objects.requireNonNull(mailTemplateApplicationAdapter);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:mail_template"})
    public Response createMailTemplate(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid CreateMailTemplateViewRequest createMailTemplateViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_TEMPLATE_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailTemplateApplicationAdapter.createMailTemplate(createMailTemplateViewRequest)))
                                .build()
                )
                .build();
    }

    @PUT
    @Path("/{mailTemplateId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"update:mail_template"})
    public Response updateMailTemplate(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("mailTemplateId") UUID mailTemplateId,
            @Valid UpdateMailTemplateViewRequest updateMailTemplateViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_TEMPLATE_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailTemplateApplicationAdapter.updateMailTemplate(mailTemplateId, updateMailTemplateViewRequest)))
                                .build()
                )
                .build();
    }

    @GET
    @Path("/{mailTemplateId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:mail_template"})
    public Response findMailTemplateById(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("mailTemplateId") UUID mailTemplateId
    ) {
        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(MAIL_TEMPLATE_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailTemplateApplicationAdapter.findMailTemplateById(mailTemplateId)))
                                .build()
                )
                .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:mail_template"})
    public Response searchMailTemplates(
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
                                .message(getMessage(MAIL_TEMPLATE_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, mailTemplateApplicationAdapter.searchMailTemplates(page, size, field, direction, keyword)))
                )
                .build();
    }
}
