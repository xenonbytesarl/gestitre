package cm.xenonbyte.gestitre.application.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleView {
    private String id;
    private String name;
    private Boolean active;
    private Set<PermissionView> permissionViews;
}
