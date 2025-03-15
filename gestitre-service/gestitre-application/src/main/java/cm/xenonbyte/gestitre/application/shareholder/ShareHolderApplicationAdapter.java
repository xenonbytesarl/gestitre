package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHolderByIdViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.SearchShareHoldersPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareHolderViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareShareHolderViewRequest;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
public interface ShareHolderApplicationAdapter {

    @Nonnull @Valid CreateShareHolderViewResponse createShareHolder(@Nonnull @Valid CreateShareHolderViewRequest createShareHolderViewRequest);

    @Nonnull @Valid UpdateShareHolderViewResponse updateShareHolder(UUID shareHolderId, @Nonnull @Valid UpdateShareShareHolderViewRequest updateShareHolderViewRequest);

    @Nonnull @Valid FindShareHolderByIdViewResponse findShareHolderById(UUID shareHolderId);

    @Nonnull SearchShareHoldersPageInfoViewResponse searchShareHolders(Integer page, Integer size, String field, String direction, String keyword);
}
