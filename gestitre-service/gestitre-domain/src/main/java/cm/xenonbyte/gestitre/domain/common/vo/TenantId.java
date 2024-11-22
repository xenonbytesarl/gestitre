package cm.xenonbyte.gestitre.domain.common.vo;

import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public class TenantId extends BaseId<UUID> {

    public TenantId(@Nonnull UUID value) {
        super(value);
    }
}
