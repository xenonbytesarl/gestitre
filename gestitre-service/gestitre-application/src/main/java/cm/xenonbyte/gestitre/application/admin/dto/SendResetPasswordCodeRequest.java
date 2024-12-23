package cm.xenonbyte.gestitre.application.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;

/**
 * @author bamk
 * @version 1.0
 * @since 23/12/2024
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SendResetPasswordCodeRequest {
    @NotEmpty(message = NOT_EMPTY)
    private String email;
}
