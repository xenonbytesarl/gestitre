package cm.xenonbyte.gestitre.domain.common.verification.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class VerificationId extends BaseId<UUID> {
    public VerificationId(@Nonnull UUID value) {
        super(value);
    }
}
