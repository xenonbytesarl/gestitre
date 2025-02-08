package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewResponse;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.ports.primary.ShareHolderService;
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
    private final ShareHolderViewMapper shareHolderViewMapper;

    public ShareHolderApplicationAdapterService(
            @Nonnull ShareHolderService shareHolderService,
            @Nonnull ShareHolderViewMapper shareHolderViewMapper) {
        this.shareHolderService = Objects.requireNonNull(shareHolderService);
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
}
