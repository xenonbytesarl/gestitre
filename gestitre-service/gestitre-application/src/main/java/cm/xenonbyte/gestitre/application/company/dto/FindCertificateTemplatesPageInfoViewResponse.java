package cm.xenonbyte.gestitre.application.company.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
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
public final class FindCertificateTemplatesPageInfoViewResponse {
    private Boolean first;

    private Boolean last;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;
    @Valid
    private List<FindCertificateTemplatesViewResponse> elements = new ArrayList<>();
}
