package cm.xenonbyte.gestitre.application.company.certificatetemplate.dto;

import cm.xenonbyte.gestitre.application.common.dto.PageInfoView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.NOT_EMPTY;

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
public  class SearchCertificateTemplatesPageInfoViewResponse extends PageInfoView {

    @Valid
    @NotEmpty(message = NOT_EMPTY)
    private List<SearchCertificateTemplatesViewResponse> elements = new ArrayList<>();
}
