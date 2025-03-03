package cm.xenonbyte.gestitre.application.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 02/03/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class ProfileResponseView {
    private UUID companyId;
    private UUID tenantId;
    private String name;
    private String language;
}
