package cm.xenonbyte.gestitre.application.stock.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
import java.util.List;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;
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
public class StockMoveView {
    @NotBlank(message = NOT_BLANK)
    @Size(max = 32, message = MAX_SIZE)
    private String reference;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    private String companyName;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 32, message = MAX_SIZE)
    private String isinCode;
    @NotNull(message = NOT_NULL)
    private StockNatureView nature;
    @NotNull(message = NOT_NULL)
    private StockMoveTypeView type;
    @NotNull(message = NOT_NULL)
    private StockMoveStateView state;
    @NotNull(message = NOT_NULL)
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    private BigInteger quantityCredit;
    @NotNull(message = NOT_NULL)
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    private BigInteger quantityDebit;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 512, message = MAX_SIZE)
    private String filename;
    @NotNull(message = NOT_NULL)
    private UUID companyId;
    @NotNull(message = NOT_NULL)
    private ZonedDateTime createdDate;
    private String observation;
    @NotEmpty(message = NOT_EMPTY)
    private List<@Valid StockMoveLineView> moveLines;

}
