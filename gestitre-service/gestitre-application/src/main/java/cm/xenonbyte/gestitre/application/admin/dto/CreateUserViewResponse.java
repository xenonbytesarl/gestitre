package cm.xenonbyte.gestitre.application.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(
        value = {"password", "confirmPassword"},
        allowGetters = true
)
public final class CreateUserViewResponse extends UserViewResponse {
}
