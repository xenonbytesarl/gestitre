package cm.xenonbyte.gestitre.domain.shareholder.event;

import cm.xenonbyte.gestitre.domain.common.event.BaseEvent;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public class ShareHolderEvent implements BaseEvent<ShareHolder> {
    private final ShareHolder shareHolder;
    private final ZonedDateTime createAt;

    public ShareHolderEvent(ShareHolder shareHolder, ZonedDateTime createAt) {
        this.shareHolder = shareHolder;
        this.createAt = createAt;
    }

    public ShareHolder getShareHolder() {
        return shareHolder;
    }

    public ZonedDateTime getCreateAt() {
        return createAt;
    }
}
