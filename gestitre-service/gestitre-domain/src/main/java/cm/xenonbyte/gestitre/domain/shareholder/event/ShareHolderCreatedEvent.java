package cm.xenonbyte.gestitre.domain.shareholder.event;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainEvent;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@DomainEvent
public final class ShareHolderCreatedEvent extends ShareHolderEvent {
    public ShareHolderCreatedEvent(ShareHolder shareHolder, ZonedDateTime createAt) {
        super(shareHolder, createAt);
    }
}
