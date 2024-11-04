package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.Image;
import cm.xenonbyte.gestitre.domain.common.vo.StorageLocation;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.file.port.primary.StorageManager;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

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

    @ConfigProperty(name = "storage.root.path.logo", defaultValue = "")
    String storageRootPathLogo;

    @ConfigProperty(name = "storage.root.path.stamp", defaultValue = "")
    String storageRootPathStamp;


    public CompanyApplicationAdapterService(
            @NotNull final CompanyService companyService,
            @Nonnull final StorageManager storageManager,
            @NotNull final CompanyApplicationViewMapper companyApplicationViewMapper) {
        this.companyService = Objects.requireNonNull(companyService);
        this.storageManager = Objects.requireNonNull(storageManager);
        this.companyApplicationViewMapper = Objects.requireNonNull(companyApplicationViewMapper);
    }


    @Override
    public CreateCompanyViewResponse createCompany(CreateCompanyViewRequest createCompanyViewRequest, FileUpload logo, FileUpload stamp) throws IOException {
        Image imageLogo = logo == null || logo.fileName() == null
                ? null
                : Image.with(Text.of(Objects.requireNonNull(logo.fileName())), Files.newInputStream(logo.filePath()))
                        .computeImageName(storageRootPath, storageRootPathLogo);
        Image imageStamp = stamp == null || stamp.fileName() == null
                ? null
                : Image.with(Text.of(Objects.requireNonNull(stamp.fileName())), Files.newInputStream(stamp.filePath()))
                        .computeImageName(storageRootPath, storageRootPathStamp);

        CompanyCreatedEvent companyCreatedEvent = companyService.createCompany(companyApplicationViewMapper.toCompany(
                createCompanyViewRequest, imageLogo, imageStamp));

        if(imageLogo != null) {
            storageManager.store(imageLogo, StorageLocation.of(Text.of(imageLogo.name().value())));
        }
        if(imageStamp != null) {
            storageManager.store(imageStamp, StorageLocation.of(Text.of(imageStamp.name().value())));
        }

        return companyApplicationViewMapper.toCompanyCreateViewResponse(companyCreatedEvent.getCompany());
    }
}
