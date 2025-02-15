package cm.xenonbyte.gestitre.application.company.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;
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
public class CompanyView {


    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    protected String companyName;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 16, message = MAX_SIZE)
    protected String code;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    protected String companyManagerName;
    @NotBlank(message = NOT_BLANK)
    protected String licence;
    @NotBlank(message = NOT_BLANK)
    protected String legalForm;
    @Valid
    @NotNull(message = NOT_NULL)
    protected AddressView address;
    @Valid
    @NotNull(message = NOT_NULL)
    protected ContactView contact;
    @Size(max = 128, message = MAX_SIZE)
    protected String activity;
    @Size(max = 64, message = MAX_SIZE)
    protected String registrationNumber;
    protected UUID certificateTemplateId;
    @Size(max = 64, message = MAX_SIZE)
    protected String webSiteUrl;
    @Size(max = 64, message = MAX_SIZE)
    protected String isinCode;
    @Size(max = 64, message = MAX_SIZE)
    protected String taxNumber;
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    protected BigDecimal grossDividendStockUnit;
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    protected BigDecimal nominalValue;


}
