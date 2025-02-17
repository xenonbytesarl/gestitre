package cm.xenonbyte.gestitre.application.company.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.POSITIVE_OR_ZERO;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyViewResponse extends CompanyView {

    @NotNull(message = NOT_NULL)
    protected UUID id;
    protected String logoFilename;
    protected String logoEncoded;
    protected String logoMimeType;
    protected String stampFilename;
    protected String stampEncoded;
    protected String stampMimeType;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected ZonedDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected ZonedDateTime endLicenceDate;
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    protected BigDecimal netDividendStock;
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    protected BigDecimal capitalization;
    @PositiveOrZero(message =POSITIVE_OR_ZERO)
    protected Long stockQuantity;
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    protected BigDecimal ircmRetain;
    @NotNull(message = NOT_NULL)
    protected Boolean active;
}
