package cm.xenonbyte.gestitre.application.notification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

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
public class MailServerViewResponse extends MailServerViewRequest {
    @NotNull(message = NOT_NULL)
    private UUID id;
    @NotNull(message = NOT_NULL)
    private MailServerStateView state;
    @NotNull(message = NOT_NULL)
    private ZonedDateTime creationAt;
    private ZonedDateTime confirmedAt;
    @NotNull(message = NOT_NULL)
    private Boolean active;

}
