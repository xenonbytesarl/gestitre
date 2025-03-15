package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.Mail;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MailJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "subject", source = "subject.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target =  "to", source = "to.text.value")
    @Mapping(target =  "cc", expression = "java(mail.getCc() == null || mail.getCc().text().value().isEmpty()? null: mail.getCc().text().value())")
    @Mapping(target =  "createdAt", source = "createdAt")
    @Mapping(target =  "sendAt", source = "sendAt")
    @Mapping(target =  "attemptToSend", expression = "java(mail.getAttemptToSend() == null? null: mail.getAttemptToSend().value())")
    @Mapping(target = "type", expression = "java(MailTemplateTypeJpa.valueOf(mail.getType().name()))")
    @Mapping(target = "state", expression = "java(MailStateJpa.valueOf(mail.getState().name()))")
    @Mapping(target = "mailTemplate", expression = "java(mail.getMailTemplateId() == null? null: MailTemplateJpa.builder().id(mail.getMailTemplateId().getValue()).build())")
    @Mapping(target = "mailServer", expression = "java(mail.getMailServerId() == null? null: MailServerJpa.builder().id(mail.getMailServerId().getValue()).build())")
    @Mapping(target = "attributes", qualifiedByName = "toAttributeJpa", source = "attributes")
    @Nonnull MailJpa toMailJpa(@Nonnull Mail mail);

    @Named("toAttributeJpa")
    default Map<String, String> toAttributeJpa(Map<Text, Text> attributes) {
        if(attributes == null) {
            return Map.of();
        }
        return attributes.entrySet().stream()
                .collect(Collectors.toMap(attribute -> attribute.getKey().value(), attribute -> attribute.getValue().value()));
    }


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "subject", target = "subject.text.value")
    @Mapping(source = "from", target = "from.text.value")
    @Mapping(source = "to", target = "to.text.value")
    @Mapping(expression = "java(mailJpa.getCc() == null || mailJpa.getCc().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Email.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(mailJpa.getCc())))", target =  "cc")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "sendAt", target = "sendAt")
    @Mapping(expression = "java(mailJpa.getAttemptToSend() == null? null: cm.xenonbyte.gestitre.domain.common.vo.Quantity.of(mailJpa.getAttemptToSend()))", target = "attemptToSend")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType.valueOf(mailJpa.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailState.valueOf(mailJpa.getState().name()))", target = "state")
    @Mapping(expression = "java(mailJpa.getMailTemplate() == null? null: new cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId(mailJpa.getMailTemplate().getId()))", target = "mailTemplateId")
    @Mapping(expression = "java(mailJpa.getMailServer() == null? null: new cm.xenonbyte.gestitre.domain.notification.vo.MailServerId(mailJpa.getMailServer().getId()))", target = "mailServerId")
    @Mapping(source = "attributes", qualifiedByName = "toAttribute", target = "attributes")
    @Nonnull Mail toMail(@Nonnull MailJpa mailJpa);

    @Named("toAttribute")
    default Map<Text, Text> toAttribute(Map<String, String> attributes) {
        if(attributes == null) {
            return Map.of();
        }
        return attributes.entrySet().stream()
                .collect(Collectors.toMap(attribute -> cm.xenonbyte.gestitre.domain.common.vo.Text.of(attribute.getKey()), attribute -> cm.xenonbyte.gestitre.domain.common.vo.Text.of(attribute.getValue())));
    }

    void copyNewToOldMailJpa(@Nonnull MailJpa newmailJpa, @Nonnull @MappingTarget MailJpa oldMailJpa);
}
