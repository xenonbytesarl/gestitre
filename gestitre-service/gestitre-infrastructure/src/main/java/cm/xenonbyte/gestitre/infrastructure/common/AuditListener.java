package cm.xenonbyte.gestitre.infrastructure.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class AuditListener {

    @PrePersist
    public void prePersist(Auditable entity) {
        entity.setCreatedAt(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));
        entity.setUpdatedAt(null);
    }

    @PreUpdate
    public void preUpdate(Auditable entity) {
        entity.setUpdatedAt(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));
    }
}
