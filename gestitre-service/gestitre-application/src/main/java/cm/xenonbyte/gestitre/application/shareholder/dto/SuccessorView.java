package cm.xenonbyte.gestitre.application.shareholder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.EMAIL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_BLANK;

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
public final class SuccessorView {
    @NotBlank(message = NOT_BLANK)
    @Size(max = 128, message = MAX_SIZE)
    private String name;
    @Email(message = EMAIL)
    private String email;
    @NotBlank(message = NOT_BLANK)
    @Size(max = 32, message = MAX_SIZE)
    private String phone;
}
