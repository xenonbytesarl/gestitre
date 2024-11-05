package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.common.dto.SuccessApiResponse;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import jakarta.annotation.Nonnull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CONTENT;
import static cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil.getMessage;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static java.util.Locale.forLanguageTag;
import static java.util.Map.of;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
@Path("/api/v1/companies")
public class CompanyResource {

    private static final String COMPANY_CREATED_SUCCESSFULLY = "CompanyResource.1";

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
            @RestForm @PartType(APPLICATION_JSON) CreateCompanyViewRequest createCompanyViewRequest
    ) throws IOException {

        return Response.status(CREATED)
                .entity(
                        SuccessApiResponse.builder()
                                .success(true)
                                .status(CREATED.name())
                                .code(CREATED.getStatusCode())
                                .timestamp(ZonedDateTime.now())
                                .message(getMessage(COMPANY_CREATED_SUCCESSFULLY, forLanguageTag(acceptLanguage) ))
                                .data(of(CONTENT, companyApplicationAdapterService.createCompany(createCompanyViewRequest, logo, stamp )))
                                .build()
                )
                .build();
    }
}
