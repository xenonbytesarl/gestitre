package cm.xenonbyte.gestitre.application.admin.dto;

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
 * @since 15/02/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class SearchUserPageInfoViewResponse extends PageInfoView {

    private List<@Valid SearchUsersViewResponse> elements = new ArrayList<>();
}
