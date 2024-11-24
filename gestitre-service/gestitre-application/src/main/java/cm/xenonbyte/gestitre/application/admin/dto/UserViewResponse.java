package cm.xenonbyte.gestitre.application.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

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
public class UserViewResponse extends UserView {
    @NotNull(message = NOT_NULL)
    private UUID id;
    @NotNull(message = NOT_NULL)
    private UUID tenantId;
    @NotNull(message = NOT_NULL)
    private Boolean accountExpired;
    @NotNull(message = NOT_NULL)
    private Boolean accountLocked;
    @NotNull(message = NOT_NULL)
    private Boolean credentialExpired;
    @NotNull(message = NOT_NULL)
    private Boolean accountEnabled;
    @NotNull(message = NOT_NULL)
    private Long failedLoginAttempt;

}
