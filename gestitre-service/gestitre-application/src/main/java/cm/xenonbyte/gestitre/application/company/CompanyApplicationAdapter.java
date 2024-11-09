package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.FindCompanyByIdViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.FindCompanyPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.SearchCompanyPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
public interface CompanyApplicationAdapter {
    @NonNull @Valid CreateCompanyViewResponse createCompany(CreateCompanyViewRequest createCompanyViewRequest, FileUpload logo, FileUpload stamp) throws IOException;

    FindCompanyPageInfoViewResponse findCompanies(Integer page, Integer size, String field, String direction);

    SearchCompanyPageInfoViewResponse searchCompanies(Integer page, Integer size, String field, String direction, String keyword);

    FindCompanyByIdViewResponse findCompanyById(UUID companyId) throws IOException;

    @NonNull @Valid
    UpdateCompanyViewResponse updateCompany(@Nonnull UUID companyId, @NonNull @Valid UpdateCompanyViewRequest updateCompanyViewRequest, FileUpload logo, FileUpload stamp) throws IOException;
}
