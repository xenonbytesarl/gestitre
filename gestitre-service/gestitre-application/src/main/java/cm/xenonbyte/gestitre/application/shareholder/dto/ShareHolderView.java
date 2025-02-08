package cm.xenonbyte.gestitre.application.shareholder.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;
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
@AllArgsConstructor
public class ShareHolderView {
    @NotBlank(message = NOT_BLANK)
    @Size(max = 128, message = MAX_SIZE)
    private String name;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    private String accountNumber;
    @NotNull(message = NOT_BLANK)
    private AccountTypeView accountTypeView;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 128, message = MAX_SIZE)
    private String taxResidence;
    @NotNull(message = NOT_NULL)
    private BigDecimal initialBalance;
    private String bankAccountNumber;
    private String administrator;
    private String email;
    private String phone;
    private String city;
    private String zipCode;
    private ShareHolderTypeView shareHolderTypeView;
    @Valid
    private RepresentativeView representativeView;
    @Valid
    private SuccessorView successorView;
    @NotNull(message = NOT_NULL)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private ZonedDateTime createdDate;
    private Boolean active;
}
