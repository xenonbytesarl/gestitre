package cm.xenonbyte.gestitre.application.stock.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StockMoveViewResponse extends StockMoveView {
    @NotNull(message = NOT_NULL)
    private UUID id;
    @NotNull(message = NOT_NULL)
    private UUID tenantId;
    private String fileEncoded;
    private String fileMimeType;
    @NotNull(message = NOT_NULL)
    private ZonedDateTime createdDate;
}
