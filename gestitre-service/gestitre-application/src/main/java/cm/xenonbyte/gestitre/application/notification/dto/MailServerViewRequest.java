package cm.xenonbyte.gestitre.application.notification.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MailServerViewRequest {
    @NotEmpty(message = NOT_EMPTY)
    private String name;
    @NotEmpty(message = NOT_EMPTY)
    private String from;
    @NotNull(message = NOT_NULL)
    private MailServerTypeView type;
    @NotEmpty(message = NOT_EMPTY)
    private String protocol;
    @NotEmpty(message = NOT_EMPTY)
    private String host;
    @NotNull(message = NOT_NULL)
    private Integer port;
    private String username;
    private String password;
    private Boolean useSSL;
    private Boolean useAuth;
}
