package cm.xenonbyte.gestitre.application.notification.dto;

import cm.xenonbyte.gestitre.application.common.dto.PageInfoView;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public final class SearchMailServerPageInfoViewResponse extends PageInfoView {

    private List<@Valid SearchMailsServerViewResponse> elements = new ArrayList<>();
}
