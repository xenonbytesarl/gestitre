package cm.xenonbyte.gestitre.application.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.EMAIL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class VerifyCodeRequest {
    @NotEmpty(message = NOT_EMPTY)
    private String code;
    @NotEmpty(message = NOT_EMPTY)
    @Email(message = EMAIL)
    private String email;
}
