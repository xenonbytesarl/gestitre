package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.infrastructure.common.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_permission")
public class PermissionJpa extends Auditable {
    @Column(name = "c_name", nullable = false, unique = true, length = 64)
    private String name;
}
