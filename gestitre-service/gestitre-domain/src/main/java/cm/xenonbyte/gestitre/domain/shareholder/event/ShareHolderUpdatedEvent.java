package cm.xenonbyte.gestitre.domain.shareholder.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 15/02/2025
 */
@DomainEvent
public final class ShareHolderUpdatedEvent extends ShareHolderEvent{
    public ShareHolderUpdatedEvent(ShareHolder shareHolder, ZonedDateTime createAt) {
        super(shareHolder, createAt);
    }
}
