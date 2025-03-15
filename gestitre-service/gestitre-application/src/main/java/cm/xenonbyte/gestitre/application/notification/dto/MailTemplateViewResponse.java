package cm.xenonbyte.gestitre.application.notification.dto;

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
 * @since 15/03/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplateViewResponse extends MailTemplateView {
    @NotNull(message = NOT_NULL)
    private UUID id;
}
