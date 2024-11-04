package cm.xenonbyte.gestitre.application.company.certificatetemplate.dto;

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
 * @since 02/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateTemplateViewResponse extends CertificateTemplateView {
    @NotNull(message = NOT_NULL)
    protected UUID id;
    @NotNull(message = NOT_NULL)
    protected Boolean active;
}
