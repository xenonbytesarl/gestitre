package cm.xenonbyte.gestitre.domain.shareholder.ports.primary;

import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public interface ShareHolderService {

    ShareHolderCreatedEvent createShareHolder(ShareHolder shareHolder);

}
