package cm.xenonbyte.gestitre.application.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.POSITIVE_OR_ZERO;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class StockMoveLineView {
    private UUID id;
    private UUID tenantId;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 32, message = MAX_SIZE)
    private String accountNumber;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    private String name;
    private String reference;
    @NotNull(message = NOT_NULL)
    private StockMoveLineTypeView type;
    @NotNull(message = NOT_NULL)
    private StockMoveLineStateView state;
    @NotNull(message = NOT_NULL)
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    private BigInteger quantity;
    private ZonedDateTime createdDate;
    private String city;
    private String zipCode;
    private String administrator;
    @NotNull(message = NOT_NULL)
    private UUID shareHolderId;
    private UUID stockMoveId;
}
