package cm.xenonbyte.gestitre.application.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.EMAIL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
    @NotEmpty(message = NOT_EMPTY)
    private String name;
    @NotEmpty(message = NOT_EMPTY)
    @Email(message = EMAIL)
    private String email;
    @NotEmpty(message = NOT_EMPTY)
    private String password;
    @NotEmpty(message = NOT_EMPTY)
    private String confirmPassword;
    @NotNull(message = NOT_NULL)
    private UUID roleId;
    @NotNull(message = NOT_NULL)
    private UUID companyId;
    private Boolean useMfa;
}
