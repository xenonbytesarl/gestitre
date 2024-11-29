package cm.xenonbyte.gestitre.application.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.EMAIL;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MIN_SIZE;
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
    protected String name;
    @NotEmpty(message = NOT_EMPTY)
    @Email(message = EMAIL)
    protected String email;
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 6, message = MIN_SIZE)
    protected String password;
    @NotEmpty(message = NOT_EMPTY)
    @Size(min = 6, message = MIN_SIZE)
    protected String confirmPassword;
    @NotNull(message = NOT_NULL)
    protected Set<RoleView> roleViews;
    @NotNull(message = NOT_NULL)
    protected UUID companyId;
    protected Boolean useMfa;
}
