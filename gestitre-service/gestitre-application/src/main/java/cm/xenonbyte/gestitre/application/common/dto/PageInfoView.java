package cm.xenonbyte.gestitre.application.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_NULL;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoView {
    @NotNull(message = NOT_NULL)
    private Boolean first;
    @NotNull(message = NOT_NULL)
    private Boolean last;
    @NotNull(message = NOT_NULL)
    private Integer pageSize;
    @NotNull(message = NOT_NULL)
    private Long totalElements;
    @NotNull(message = NOT_NULL)
    private Integer totalPages;
}
