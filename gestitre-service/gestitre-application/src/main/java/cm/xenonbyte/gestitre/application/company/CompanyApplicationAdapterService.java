package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.FindCompanyByIdViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.SearchCompanyPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.Image;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.StorageLocation;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.file.port.primary.StorageManager;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
@Slf4j
@ApplicationScoped
public final class CompanyApplicationAdapterService implements CompanyApplicationAdapter {

    private final CompanyService companyService;
    private final StorageManager storageManager;
    private final CompanyApplicationViewMapper companyApplicationViewMapper;

    @ConfigProperty(name = "storage.root.path", defaultValue = "")
    String storageRootPath;

    @ConfigProperty(name = "storage.root.company.path.logo", defaultValue = "")
    String storageRootCompanyPathLogo;

    @ConfigProperty(name = "storage.root.company.path.stamp", defaultValue = "")
    String storageRootCompanyPathStamp;


    public CompanyApplicationAdapterService(
            @NotNull final CompanyService companyService,
            @Nonnull final StorageManager storageManager,
            @NotNull final CompanyApplicationViewMapper companyApplicationViewMapper) {
        this.companyService = Objects.requireNonNull(companyService);
        this.storageManager = Objects.requireNonNull(storageManager);
        this.companyApplicationViewMapper = Objects.requireNonNull(companyApplicationViewMapper);
    }


    @Override
    @Transactional
    public CreateCompanyViewResponse createCompany(CreateCompanyViewRequest createCompanyViewRequest, FileUpload logo, FileUpload stamp) throws IOException {
        Image imageLogo = logo == null || logo.fileName() == null
                ? null
                : Image.with(Text.of(Objects.requireNonNull(logo.fileName())), Files.newInputStream(logo.filePath()))
                        .computeImageName(storageRootPath, storageRootCompanyPathLogo);
        Image imageStamp = stamp == null || stamp.fileName() == null
                ? null
                : Image.with(Text.of(Objects.requireNonNull(stamp.fileName())), Files.newInputStream(stamp.filePath()))
                        .computeImageName(storageRootPath, storageRootCompanyPathStamp);

        CompanyCreatedEvent companyCreatedEvent = companyService.createCompany(companyApplicationViewMapper.toCompany(
                createCompanyViewRequest, imageLogo, imageStamp));

        saveImage(imageLogo, imageStamp);

        CreateCompanyViewResponse companyCreateViewResponse =
                companyApplicationViewMapper.toCompanyCreateViewResponse(companyCreatedEvent.getCompany());

        GetEncodedFiles getEncodedFiles = getGetEncodedFiles(companyCreatedEvent.getCompany());

        companyCreateViewResponse.setLogoMimeType(getEncodedFiles.logoMimeType());
        companyCreateViewResponse.setLogoEncoded(getEncodedFiles.logoEncoded());
        companyCreateViewResponse.setStampMimeType(getEncodedFiles.stampMimeType());
        companyCreateViewResponse.setStampEncoded(getEncodedFiles.stampEncoded());

        return companyCreateViewResponse;
    }

    @Override
    public SearchCompanyPageInfoViewResponse searchCompanies(Integer page, Integer size, String field, String direction, String keyword) {
        PageInfo<Company> companyPageInfo = companyService.searchCompanies(
                PageInfoPage.of(page),
                PageInfoSize.of(size),
                PageInfoField.of(Text.of(field)),
                PageInfoDirection.valueOf(direction),
                Keyword.of(Text.of(keyword))
        );
        return companyApplicationViewMapper.toSearchCompanyPageInfoViewResponse(companyPageInfo);
    }

    @Override
    public FindCompanyByIdViewResponse findCompanyById(UUID companyId) throws IOException {
        Company company = companyService.findCompanyById(new CompanyId(companyId));
        FindCompanyByIdViewResponse findByIdViewResponse = companyApplicationViewMapper.toFindByIdViewResponse(company);
        GetEncodedFiles result = getGetEncodedFiles(company);
        findByIdViewResponse.setLogoEncoded(result.logoEncoded());
        findByIdViewResponse.setLogoMimeType(result.logoMimeType());
        findByIdViewResponse.setStampEncoded(result.stampEncoded());
        findByIdViewResponse.setStampMimeType(result.stampMimeType());
        return findByIdViewResponse;
    }



    @Override
    @Transactional
    public @NonNull UpdateCompanyViewResponse updateCompany(@Nonnull UUID companyId, @NonNull UpdateCompanyViewRequest updateCompanyViewRequest, FileUpload logo, FileUpload stamp) throws IOException {
        Image imageLogo = logo == null || logo.fileName() == null
                ? null
                : updateCompanyViewRequest.getLogoFilename() == null || updateCompanyViewRequest.getLogoFilename().isEmpty()
                    ?
                        Image.with(Text.of(Objects.requireNonNull(logo.fileName())), Files.newInputStream(logo.filePath()))
                            .computeImageName(storageRootPath, storageRootCompanyPathLogo)
                    :  Image.with(Text.of(updateCompanyViewRequest.getLogoFilename()), Files.newInputStream(logo.filePath()));

        Image imageStamp = stamp == null || stamp.fileName() == null
                ? null
                : updateCompanyViewRequest.getStampFilename() == null || updateCompanyViewRequest.getStampFilename().isEmpty()
                    ?
                        Image.with(Text.of(Objects.requireNonNull(stamp.fileName())), Files.newInputStream(stamp.filePath()))
                            .computeImageName(storageRootPath, storageRootCompanyPathStamp)
                    :   Image.with(Text.of(updateCompanyViewRequest.getStampFilename()), Files.newInputStream(stamp.filePath()));

        CompanyUpdatedEvent companyUpdatedEvent = companyService.updateCompany(new CompanyId(companyId), companyApplicationViewMapper.toCompany(
                updateCompanyViewRequest, imageLogo, imageStamp));

        saveImage(imageLogo, imageStamp);

        UpdateCompanyViewResponse updateCompanyViewResponse =
                companyApplicationViewMapper.toUpdateCompanyViewResponse(companyUpdatedEvent.getCompany());

        GetEncodedFiles getEncodedFiles = getGetEncodedFiles(companyUpdatedEvent.getCompany());

        updateCompanyViewResponse.setLogoMimeType(getEncodedFiles.logoMimeType());
        updateCompanyViewResponse.setLogoEncoded(getEncodedFiles.logoEncoded());
        updateCompanyViewResponse.setStampMimeType(getEncodedFiles.stampMimeType());
        updateCompanyViewResponse.setStampEncoded(getEncodedFiles.stampEncoded());

        return updateCompanyViewResponse;
    }

    private void saveImage(Image imageLogo, Image imageStamp) {
        if(imageLogo != null) {
            storageManager.store(imageLogo, StorageLocation.of(Text.of(imageLogo.name().value())));
        }
        if(imageStamp != null) {
            storageManager.store(imageStamp, StorageLocation.of(Text.of(imageStamp.name().value())));
        }
    }

    private GetEncodedFiles getGetEncodedFiles(Company company) throws IOException {
        String logoEncoded = company.getLogoFilename() == null || company.getLogoFilename().text().value().isEmpty()? null: storageManager.fileToBase64(company.getLogoFilename().text().value());
        String logoMimeType = company.getLogoFilename() == null || company.getLogoFilename().text().value().isEmpty()? null: storageManager.mimeType(company.getLogoFilename().text().value());
        String stampEncoded = company.getStampFilename() == null || company.getStampFilename().text().value().isEmpty()? null: storageManager.fileToBase64(company.getStampFilename().text().value());
        String stampMimeType = company.getStampFilename() == null || company.getStampFilename().text().value().isEmpty()? null: storageManager.mimeType(company.getStampFilename().text().value());
        return new GetEncodedFiles(logoEncoded, logoMimeType, stampEncoded, stampMimeType);
    }

    private record GetEncodedFiles(String logoEncoded, String logoMimeType, String stampEncoded, String stampMimeType) {}
}
