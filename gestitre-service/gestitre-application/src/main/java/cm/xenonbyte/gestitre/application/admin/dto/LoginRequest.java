package cm.xenonbyte.gestitre.application.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MIN_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class LoginRequest {
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 10, max = 10, message = NOT_EMPTY)
    private String tenantCode;
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 3, message = MIN_SIZE)
    private String email;
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 6, message = MIN_SIZE)
    private String password;
}
