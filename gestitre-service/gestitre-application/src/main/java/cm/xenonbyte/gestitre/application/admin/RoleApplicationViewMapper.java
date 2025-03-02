package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.SearchRolesPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.SearchRolesViewResponse;
import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 02/03/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RoleApplicationViewMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchRolesViewResponses", target = "elements")
    @Nonnull @Valid SearchRolesPageInfoViewResponse toSearchRolesPageInfoViewResponse(@Nonnull PageInfo<Role> rolePageInfo);

    @Named("toSearchRolesViewResponses")
    @Nonnull @Valid List<SearchRolesViewResponse> toSearchRolesViewResponses(@Nonnull List<Role> roles);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid SearchRolesViewResponse toSearchRolesViewResponse(@Nonnull Role role);
}
