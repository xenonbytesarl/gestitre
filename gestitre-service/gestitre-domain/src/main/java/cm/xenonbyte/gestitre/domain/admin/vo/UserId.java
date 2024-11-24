package cm.xenonbyte.gestitre.domain.admin.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class UserId extends BaseId<UUID> {
    public UserId(@Nonnull UUID value) {
        super(value);
    }
}
