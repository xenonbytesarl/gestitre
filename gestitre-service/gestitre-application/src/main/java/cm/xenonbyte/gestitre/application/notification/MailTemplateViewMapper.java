package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.FindByIdMailTemplateViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailTemplatePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailTemplatesViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
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
 * @since 15/03/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MailTemplateViewMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType.valueOf(createMailTemplateViewRequest.getType().name()))", target = "type")
    @Mapping(expression = "java(createMailTemplateViewRequest.getActive() == null? null: cm.xenonbyte.gestitre.domain.common.vo.Active.with(createMailTemplateViewRequest.getActive()))", target = "active")
    @Nonnull MailTemplate toMailTemplate(@Nonnull CreateMailTemplateViewRequest createMailTemplateViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailTemplateTypeView.valueOf(mailTemplate.getType().name()))")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid CreateMailTemplateViewResponse toCreateMailTemplateviewResponse(@Nonnull MailTemplate mailTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType.valueOf(updateMailTemplateViewRequest.getType().name()))", target = "type")
    @Mapping(source = "active", target = "active.value")
    @Nonnull MailTemplate toMailTemplate(@Nonnull UpdateMailTemplateViewRequest updateMailTemplateViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailTemplateTypeView.valueOf(mailTemplate.getType().name()))")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid
    UpdateMailTemplateViewResponse toUpdateMailTemplateviewResponse(@Nonnull MailTemplate mailTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailTemplateTypeView.valueOf(mailTemplate.getType().name()))")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid FindByIdMailTemplateViewResponse toFindByIdMailTemplateViewResponse(@Nonnull MailTemplate mailTemplate);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchMailTemplatesViewResponses", target = "elements")
    @Nonnull @Valid SearchMailTemplatePageInfoViewResponse toSearchMailTemplatePageInfoViewResponse(@Nonnull PageInfo<MailTemplate> mailTemplatePageInfo);

    @Named("toSearchMailTemplatesViewResponses")
    @Nonnull @Valid List<SearchMailTemplatesViewResponse> toSearchMailTemplatesViewResponses(@Nonnull List<MailTemplate> mailTemplates);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "name", source = "name.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailTemplateTypeView.valueOf(mailTemplate.getType().name()))")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid SearchMailTemplatesViewResponse toSearchMailTemplatesViewResponse(@Nonnull MailTemplate mailTemplate);
}
