package cm.xenonbyte.gestitre.domain.shareholder.ports.secondary.message;

import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderEventType;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public interface ShareHolderMessagePublisher {
    void publish(ShareHolderEvent shareHolderEvent, ShareHolderEventType shareHolderEventType);
}
