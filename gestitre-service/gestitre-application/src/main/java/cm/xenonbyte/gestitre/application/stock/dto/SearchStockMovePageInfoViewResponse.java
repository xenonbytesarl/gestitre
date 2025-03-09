package cm.xenonbyte.gestitre.application.stock.dto;

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
 * @since 08/03/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class SearchStockMovePageInfoViewResponse extends PageInfoView {

    @Valid
    private List<SearchStockMovesViewResponse> elements = new ArrayList<>();
}
