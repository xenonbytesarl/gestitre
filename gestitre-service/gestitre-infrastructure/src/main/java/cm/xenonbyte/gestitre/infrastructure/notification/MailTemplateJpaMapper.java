package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MailTemplateJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "active", source = "active.value")
    @Mapping(target = "type", expression = "java(MailTemplateTypeJpa.valueOf(mailTemplate.getType().name()))")
    @Nonnull MailTemplateJpa toMailTemplateJpa(@Nonnull MailTemplate mailTemplate);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source =  "id", target = "id.value")
    @Mapping(source =  "name", target = "name.text.value")
    @Mapping(source =  "active", target = "active.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType.valueOf(mailTemplateJpa.getType().name()))", target = "type")
    @Nonnull MailTemplate toMailTemplate(@Nonnull MailTemplateJpa mailTemplateJpa);

    void copyNewToOldMailTemplateJpa(@Nonnull MailTemplateJpa newMailTemplateJpa, @Nonnull @MappingTarget MailTemplateJpa oldMailTemplateJpa);
}
