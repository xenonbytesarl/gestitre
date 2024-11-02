package cm.xenonbyte.gestitre.application.company.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author bamk
 * @version 1.0
 * @since 02/11/2024
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateCertificateTemplateViewRequest {


    @NotEmpty
    private String name;
}
