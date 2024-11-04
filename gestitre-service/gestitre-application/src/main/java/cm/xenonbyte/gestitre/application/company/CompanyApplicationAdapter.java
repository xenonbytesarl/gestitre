package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewResponse;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
public interface CompanyApplicationAdapter {
    CreateCompanyViewResponse createCompany(CreateCompanyViewRequest createCompanyViewRequest, FileUpload logo, FileUpload stamp) throws IOException;
}
