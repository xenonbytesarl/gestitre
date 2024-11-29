package cm.xenonbyte.gestitre.domain.common.vo;

import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class MailServerId extends BaseId<UUID> {
    public MailServerId(@Nonnull UUID value) {
        super(value);
    }
}
