package cm.xenonbyte.gestitre.application.shareholder.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ShareHolderResponseView extends ShareHolderView {
    @NotNull(message = NOT_NULL)
    private UUID id;
    @NotNull(message = NOT_NULL)
    private UUID tenantId;
}
