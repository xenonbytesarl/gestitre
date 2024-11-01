package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.vo.BaseId;
import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CertificateTemplateId extends BaseId<UUID> {
    public CertificateTemplateId(@Nonnull UUID value) {
        super(value);
    }
}
