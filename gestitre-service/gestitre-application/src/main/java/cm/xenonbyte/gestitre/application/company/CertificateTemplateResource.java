package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationService;
import cm.xenonbyte.gestitre.application.company.dto.CreateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCertificateTemplateViewRequest;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.OK;
import static java.util.Map.of;

/**
 /**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */

@Path("/api/v1/certificate-templates")
public class CertificateTemplateResource {

    private static final String CERTIFICATE_TEMPLATE_CREATED_SUCCESSFULLY = "CertificateTemplateResource.1";
    private static final String CERTIFICATE_TEMPLATE_FIND_SUCCESSFULLY = "CertificateTemplateResource.2";
    private static final String CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY = "CertificateTemplateResource.3";
    private static final String CONTENT = "content";
    private static final String CERTIFICATE_TEMPLATE_UPDATED_SUCCESSFULLY = "CertificateTemplateResource.4";


    private final CertificateTemplateApplicationAdapter certificateTemplateApplicationAdapter;
    private final LocalizationService localizationService;

    public CertificateTemplateResource(
            @Nonnull final CertificateTemplateApplicationAdapter certificateTemplateApplicationAdapter,
            @Nonnull final LocalizationService localizationService) {
        this.certificateTemplateApplicationAdapter = Objects.requireNonNull(certificateTemplateApplicationAdapter);
        this.localizationService = Objects.requireNonNull(localizationService);
    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createCertificateTemplate(@Valid CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest) {
        return Response.status(CREATED)
                .entity(
                    SuccessApiResponse.builder()
                        .success(true)
                        .status(CREATED.name())
                        .code(CREATED.getStatusCode())
                        .timestamp(ZonedDateTime.now())
                        .message(localizationService.getMessage(CERTIFICATE_TEMPLATE_CREATED_SUCCESSFULLY))
                        .data(of(CONTENT, certificateTemplateApplicationAdapter.createCertificateTemplate(createCertificateTemplateViewRequest)))
                        .build()
                )
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Response findCertificateTemplateById(@PathParam("id") UUID certificateTemplateId) {
        return Response.status(OK)
                .entity(
                    SuccessApiResponse.builder()
                        .success(true)
                        .status(OK.name())
                        .code(OK.getStatusCode())
                        .timestamp(ZonedDateTime.now())
                        .message(localizationService.getMessage(CERTIFICATE_TEMPLATE_FIND_SUCCESSFULLY))
                        .data(of(CONTENT, certificateTemplateApplicationAdapter.findCertificateTemplateById(certificateTemplateId)))
                )
                .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response findCertificateTemplates(
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
                                .message(localizationService.getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY))
                                .data(of(CONTENT, certificateTemplateApplicationAdapter.findCertificateTemplates(page, size, field, direction)))
                )
                .build();
    }


    @GET
    @Path("/search")
    @Produces(APPLICATION_JSON)
    public Response searchCertificateTemplates(
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
                                .message(localizationService.getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY))
                                .data(of(CONTENT, certificateTemplateApplicationAdapter.searchCertificateTemplates(page, size, field, direction, keyword)))
                )
                .build();
    }

    @PUT
    @Path("/{certificateTemplateId}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createCertificateTemplate(@PathParam("certificateTemplateId") UUID certificateTemplateId, @Valid UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(localizationService.getMessage(CERTIFICATE_TEMPLATE_UPDATED_SUCCESSFULLY))
                                .data(of(CONTENT, certificateTemplateApplicationAdapter.updateCertificateTemplate(certificateTemplateId, updateCertificateTemplateViewRequest)))
                                .build()
                )
                .build();
    }


}
