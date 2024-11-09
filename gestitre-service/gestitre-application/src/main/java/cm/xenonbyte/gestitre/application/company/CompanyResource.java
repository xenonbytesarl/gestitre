package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewRequest;
import jakarta.annotation.Nonnull;
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
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.COMPANY_API_PATH;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
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
 * @since 04/11/2024
 */
@Path(COMPANY_API_PATH)
public class CompanyResource {

    public static final String COMPANY_CREATED_SUCCESSFULLY = "CompanyResource.1";
    public static final String COMPANY_FINDS_SUCCESSFULLY = "CompanyResource.2";
    public static final String COMPANY_FIND_SUCCESSFULLY = "CompanyResource.3";
    public static final String COMPANY_UPDATED_SUCCESSFULLY = "CompanyResource.4";

    private final CompanyApplicationAdapterService companyApplicationAdapterService;

    public CompanyResource(
            @Nonnull CompanyApplicationAdapterService companyApplicationAdapterService) {
        this.companyApplicationAdapterService = Objects.requireNonNull(companyApplicationAdapterService);
    }

    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_JSON)
    public Response createCompany(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @RestForm("logo") FileUpload logo,
            @RestForm("stamp") FileUpload stamp,
            @RestForm @PartType(APPLICATION_JSON) @Valid CreateCompanyViewRequest createCompanyViewRequest
    ) throws IOException {

        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(COMPANY_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, companyApplicationAdapterService.createCompany(createCompanyViewRequest, logo, stamp )))
                                .build()
                )
                .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response findCompanies(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("field") String field,
            @QueryParam("direction") String direction
    ) {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                            .success(true)
                            .status(OK.name())
                            .code(OK.getStatusCode())
                            .timestamp(ZonedDateTime.now())
                            .message(getMessage(COMPANY_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                            .data(of(CONTENT, companyApplicationAdapterService.findCompanies(page, size, field, direction)))
                )
                .build();
    }

    @GET
    @Path("/search")
    @Produces(APPLICATION_JSON)
    public Response searchCompanies(
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
                                .message(getMessage(COMPANY_FINDS_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, companyApplicationAdapterService.searchCompanies(page, size, field, direction, keyword)))
                )
                .build();
    }

    @GET
    @Path("/{companyId}")
    @Produces(APPLICATION_JSON)
    public Response searchCompanies(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("companyId") UUID companyId
    ) throws IOException {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(COMPANY_FIND_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, companyApplicationAdapterService.findCompanyById(companyId)))
                )
                .build();
    }

    @PUT
    @Path("/{companyId}")
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(APPLICATION_JSON)
    public Response searchCompanies(
            @HeaderParam("Accept-Language") String acceptLanguage,
            @PathParam("companyId") UUID companyId,
            @RestForm("logo") FileUpload logo,
            @RestForm("stamp") FileUpload stamp,
            @RestForm @PartType(APPLICATION_JSON) @Valid UpdateCompanyViewRequest updateCompanyViewRequest
    ) throws IOException {
        return Response.status(OK)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(OK.name())
                                .code(OK.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(COMPANY_UPDATED_SUCCESSFULLY, forLanguageTag(acceptLanguage)))
                                .data(of(CONTENT, companyApplicationAdapterService.updateCompany(companyId, updateCompanyViewRequest, logo, stamp)))
                )
                .build();
    }
}
