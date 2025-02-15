package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHoldersPageInfoViewResponse;
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
import cm.xenonbyte.gestitre.domain.shareholder.ports.primary.ShareHolderService;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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
    public FindShareHoldersPageInfoViewResponse findShareHolders(Integer page, Integer size, String field, String direction, String keyword) {
        Tenant tenant = TenantContext.current() == null? null : tenantService.findTenantById(new TenantId(TenantContext.current()));
        Company company = tenant == null? null: companyService.findCompanyByName(tenant.getName());
        PageInfo<ShareHolder> shareHolderPageInfo = shareHolderService.searchShareHolders(
                PageInfoPage.of(page),
                PageInfoSize.of(size),
                PageInfoField.of(Text.of(field)),
                PageInfoDirection.valueOf(direction),
                Keyword.of(Text.of(keyword))
        );

        FindShareHoldersPageInfoViewResponse findShareHolderPageInfoViewResponse
                = shareHolderViewMapper.toFindShareHoldersPageInfoViewResponse(shareHolderPageInfo);
        if(company == null) {
            return findShareHolderPageInfoViewResponse
                    ;
        }
        return FindShareHoldersPageInfoViewResponse.builder()
                .pageSize(findShareHolderPageInfoViewResponse.getPageSize())
                .pageSize(findShareHolderPageInfoViewResponse.getPageSize())
                .totalPages(findShareHolderPageInfoViewResponse.getTotalPages())
                .totalElements(findShareHolderPageInfoViewResponse.getTotalElements())
                .first(findShareHolderPageInfoViewResponse.getFirst())
                .last(findShareHolderPageInfoViewResponse.getLast())
                .elements(
                        findShareHolderPageInfoViewResponse
                                .getElements().stream()
                                .map(value -> value.addCompanyInfo(company))
                                .toList()
                )
                .build();
    }
}
