package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHolderByIdResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHoldersPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareHolderResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareShareHolderRequestView;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
public interface ShareHolderApplicationAdapter {

    @Nonnull @Valid
    CreateShareHolderResponseView createShareHolder(@Nonnull @Valid CreateShareHolderViewRequest createShareHolderViewRequest);

    @Nonnull @Valid
    UpdateShareHolderResponseView updateShareHolder(UUID shareHolderId, @Nonnull @Valid UpdateShareShareHolderRequestView updateShareHolderViewRequest);

    @Nonnull @Valid
    FindShareHolderByIdResponseView findShareHolderById(UUID shareHolderId);

    @Nonnull FindShareHoldersPageInfoViewResponse findShareHolders(Integer page, Integer size, String field, String direction, String keyword);
}
