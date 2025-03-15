package cm.xenonbyte.gestitre.application.company.certificatetemplate;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewRequest;
import jakarta.annotation.Nonnull;
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

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CERTIFICATE_TEMPLATE_API_PATH;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 /**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */

@Path(CERTIFICATE_TEMPLATE_API_PATH)
public class CertificateTemplateResource {

    public static final String CERTIFICATE_TEMPLATE_CREATED_SUCCESSFULLY = "CertificateTemplateResource.1";
    public static final String CERTIFICATE_TEMPLATE_FIND_SUCCESSFULLY = "CertificateTemplateResource.2";
    public static final String CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY = "CertificateTemplateResource.3";
    public static final String CERTIFICATE_TEMPLATE_UPDATED_SUCCESSFULLY = "CertificateTemplateResource.4";


    private final CertificateTemplateApplicationAdapter certificateTemplateApplicationAdapter;

    public CertificateTemplateResource(
            @Nonnull final CertificateTemplateApplicationAdapter certificateTemplateApplicationAdapter) {
        this.certificateTemplateApplicationAdapter = Objects.requireNonNull(certificateTemplateApplicationAdapter);
    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"create:certificate_template"})
    public Response createCertificateTemplate(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @Valid CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest
    ) {
        return Response.status(CREATED)
                .entity(
                    SuccessApiResponse.builder()
                        .success(true)
                        .status(CREATED.name())
                        .code(CREATED.getStatusCode())
                        .timestamp(ZonedDateTime.now())
                        .message(getMessage(CERTIFICATE_TEMPLATE_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                        .data(of(CONTENT, certificateTemplateApplicationAdapter.createCertificateTemplate(createCertificateTemplateViewRequest)))
                        .build()
                )
                .build();
    }

    @GET
    @Path("/{certificateTemplateId}")
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:certificate_template"})
    public Response findCertificateTemplateById(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("certificateTemplateId") UUID certificateTemplateId) {
        return Response.status(OK)
                .entity(
                    SuccessApiResponse.builder()
                        .success(true)
                        .status(OK.name())
                        .code(OK.getStatusCode())
                        .timestamp(ZonedDateTime.now())
                        .message(getMessage(CERTIFICATE_TEMPLATE_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                        .data(of(CONTENT, certificateTemplateApplicationAdapter.findCertificateTemplateById(certificateTemplateId)))
                )
                .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:certificate_template"})
    public Response findCertificateTemplates(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("field") String field,
            @QueryParam("direction") String direction) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, certificateTemplateApplicationAdapter.findCertificateTemplates(page, size, field, direction)))
                )
                .build();
    }


    @GET
    @Path("/search")
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"read:certificate_template"})
    public Response searchCertificateTemplates(
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
                                .message(getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, certificateTemplateApplicationAdapter.searchCertificateTemplates(page, size, field, direction, keyword)))
                )
                .build();
    }

    @PUT
    @Path("/{certificateTemplateId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed({"update:certificate_template"})
    public Response updateCertificateTemplate(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("certificateTemplateId") UUID certificateTemplateId,
            @Valid UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(CERTIFICATE_TEMPLATE_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, certificateTemplateApplicationAdapter.updateCertificateTemplate(certificateTemplateId, updateCertificateTemplateViewRequest)))
                                .build()
                )
                .build();
    }


}
