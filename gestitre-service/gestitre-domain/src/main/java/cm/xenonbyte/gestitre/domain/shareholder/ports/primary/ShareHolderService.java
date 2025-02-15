package cm.xenonbyte.gestitre.domain.shareholder.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderUpdatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public interface ShareHolderService {

    @Nonnull ShareHolderCreatedEvent createShareHolder(@Nonnull ShareHolder shareHolder);

    @Nonnull ShareHolderUpdatedEvent updateShareHolder(@Nonnull ShareHolderId shareHolderId, @Nonnull ShareHolder shareHolder);

    @Nonnull ShareHolder findShareHolderById(@Nonnull ShareHolderId shareHolderId);

    PageInfo<ShareHolder> searchShareHolders(
            PageInfoPage pageInfoPage, PageInfoSize pageInfoSize, PageInfoField pageInfoField, PageInfoDirection pageInfoDirection, Keyword keyword);
}
