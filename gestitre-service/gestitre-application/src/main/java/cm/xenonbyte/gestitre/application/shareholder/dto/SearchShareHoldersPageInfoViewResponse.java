package cm.xenonbyte.gestitre.application.shareholder.dto;

import cm.xenonbyte.gestitre.application.common.dto.PageInfoView;
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
 * @since 09/02/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class SearchShareHoldersPageInfoViewResponse extends PageInfoView {

    private List<@Valid SearchShareHoldersViewResponse> elements = new ArrayList<>();
}
