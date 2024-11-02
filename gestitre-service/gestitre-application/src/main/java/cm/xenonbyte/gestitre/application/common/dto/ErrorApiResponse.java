package cm.xenonbyte.gestitre.application.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
public class ErrorApiResponse extends BaseApiResponse {
    @NotEmpty
    public String reason;
    @NotEmpty
    public List<ValidationError> errors;
}
