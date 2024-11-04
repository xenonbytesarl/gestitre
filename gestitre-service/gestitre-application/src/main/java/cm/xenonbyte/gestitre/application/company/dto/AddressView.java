package cm.xenonbyte.gestitre.application.company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;


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
public class AddressView {

    @Size(max = 128, message = MAX_SIZE)
    protected String street;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    protected String city;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 32, message = MAX_SIZE)
    protected String zipCode;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 64, message = MAX_SIZE)
    protected String country;
}
