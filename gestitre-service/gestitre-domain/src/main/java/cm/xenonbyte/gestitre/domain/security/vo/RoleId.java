package cm.xenonbyte.gestitre.domain.security.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class RoleId extends BaseId<UUID> {
    public RoleId(@Nonnull UUID value) {
        super(value);
    }
}
