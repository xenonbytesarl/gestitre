package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHoldersPageInfoViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
public interface ShareHolderApplicationAdapter {

    @Nonnull @Valid
    CreateShareHolderViewResponse createShareHolder(@Nonnull @Valid CreateShareHolderViewRequest createShareHolderViewRequest);

    @Nonnull FindShareHoldersPageInfoViewResponse findShareHolders(Integer page, Integer size, String field, String direction, String keyword);
}
