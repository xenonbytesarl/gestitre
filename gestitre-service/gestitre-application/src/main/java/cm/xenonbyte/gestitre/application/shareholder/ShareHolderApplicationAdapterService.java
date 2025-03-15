package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHolderByIdViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.SearchShareHoldersPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareHolderViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareShareHolderViewRequest;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.context.TenantContext;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderUpdatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.ports.primary.ShareHolderService;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
@Slf4j
@ApplicationScoped
public final class ShareHolderApplicationAdapterService implements ShareHolderApplicationAdapter {

    private final ShareHolderService shareHolderService;
    private final CompanyService companyService;
    private final TenantService tenantService;
    private final ShareHolderViewMapper shareHolderViewMapper;

    public ShareHolderApplicationAdapterService(
            @Nonnull ShareHolderService shareHolderService,
            @Nonnull CompanyService companyService,
            @Nonnull TenantService tenantService,
            @Nonnull ShareHolderViewMapper shareHolderViewMapper) {
        this.shareHolderService = Objects.requireNonNull(shareHolderService);
        this.companyService = Objects.requireNonNull(companyService);
        this.tenantService = Objects.requireNonNull(tenantService);
        this.shareHolderViewMapper = Objects.requireNonNull(shareHolderViewMapper);
    }

    @Nonnull
    @Override
    public CreateShareHolderViewResponse createShareHolder(@Nonnull CreateShareHolderViewRequest createShareHolderViewRequest) {
        ShareHolderCreatedEvent shareHolderCreatedEvent = shareHolderService.createShareHolder(
                shareHolderViewMapper.toShareHolder(createShareHolderViewRequest)
        );
        return shareHolderViewMapper.toCreateShareHolderViewResponse(shareHolderCreatedEvent.getShareHolder());
    }

    @Nonnull
    @Override
    public UpdateShareHolderViewResponse updateShareHolder(UUID shareHolderId, @Nonnull UpdateShareShareHolderViewRequest updateShareHolderViewRequest) {
        ShareHolderUpdatedEvent shareHolderUpdatedEvent = shareHolderService.updateShareHolder(new ShareHolderId(shareHolderId), shareHolderViewMapper.toShareHolder(updateShareHolderViewRequest));
        return shareHolderViewMapper.toUpdateShareHolderViewResponse(shareHolderUpdatedEvent.getShareHolder());
    }

    @Nonnull
    @Override
    public FindShareHolderByIdViewResponse findShareHolderById(UUID shareHolderId) {
        return shareHolderViewMapper.toFindShareHolderByIdViewResponse(
                shareHolderService.findShareHolderById(new ShareHolderId(shareHolderId)));
    }

    @Nonnull
    @Override
    public SearchShareHoldersPageInfoViewResponse searchShareHolders(Integer page, Integer size, String field, String direction, String keyword) {
        Tenant tenant = TenantContext.current() == null? null : tenantService.findTenantById(new TenantId(TenantContext.current()));
        Company company = tenant == null? null: companyService.findCompanyByName(tenant.getName());
        PageInfo<ShareHolder> shareHolderPageInfo = shareHolderService.searchShareHolders(
                PageInfoPage.of(page),
                PageInfoSize.of(size),
                PageInfoField.of(Text.of(field)),
                PageInfoDirection.valueOf(direction),
                Keyword.of(Text.of(keyword))
        );

        SearchShareHoldersPageInfoViewResponse searchShareHolderPageInfoViewResponse
                = shareHolderViewMapper.toSearchShareHoldersPageInfoViewResponse(shareHolderPageInfo);
        if(company == null) {
            return searchShareHolderPageInfoViewResponse
                    ;
        }
        return SearchShareHoldersPageInfoViewResponse.builder()
                .pageSize(searchShareHolderPageInfoViewResponse.getPageSize())
                .pageSize(searchShareHolderPageInfoViewResponse.getPageSize())
                .totalPages(searchShareHolderPageInfoViewResponse.getTotalPages())
                .totalElements(searchShareHolderPageInfoViewResponse.getTotalElements())
                .first(searchShareHolderPageInfoViewResponse.getFirst())
                .last(searchShareHolderPageInfoViewResponse.getLast())
                .elements(
                        searchShareHolderPageInfoViewResponse
                                .getElements().stream()
                                .map(value -> value.addCompanyInfo(company))
                                .toList()
                )
                .build();
    }
}
