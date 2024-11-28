package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.infrastructure.common.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

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
@Table(name = "t_role")
public class RoleJpa extends Auditable {
    @Column(name = "c_name", nullable = false, unique = true, length = 64)
    private String name;
    @Column(name = "c_active", nullable = false)
    private Boolean active;
    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "t_role_permission",
            joinColumns = @JoinColumn(name = "c_role_id", referencedColumnName = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "c_permission_id", referencedColumnName = "c_id")
    )
    private Set<PermissionJpa> permissionsJpa;
}
