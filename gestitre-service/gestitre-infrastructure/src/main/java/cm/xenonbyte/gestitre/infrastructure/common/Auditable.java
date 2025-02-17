package cm.xenonbyte.gestitre.infrastructure.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditListener.class)
public class Auditable {
    @Id
    @Column(name = "c_id", nullable = false, unique = true)
    protected UUID id;
    @Column(name = "c_created_at", nullable = false, updatable = false)
    protected ZonedDateTime createdAt;
    @Column(name = "c_updated_at", insertable = false)
    protected ZonedDateTime updatedAt;
}
