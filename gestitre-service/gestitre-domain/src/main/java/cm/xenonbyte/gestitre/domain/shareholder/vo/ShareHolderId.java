package cm.xenonbyte.gestitre.domain.shareholder.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 03/02/2025
 */
public final class ShareHolderId extends BaseId<UUID> {
    public ShareHolderId(@Nonnull UUID value) {
        super(value);
    }
}
