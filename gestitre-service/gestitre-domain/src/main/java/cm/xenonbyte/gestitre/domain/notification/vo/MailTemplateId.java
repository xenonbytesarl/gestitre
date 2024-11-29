package cm.xenonbyte.gestitre.domain.notification.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class MailTemplateId extends BaseId<UUID> {
    public MailTemplateId(@Nonnull UUID value) {
        super(value);
    }
}
