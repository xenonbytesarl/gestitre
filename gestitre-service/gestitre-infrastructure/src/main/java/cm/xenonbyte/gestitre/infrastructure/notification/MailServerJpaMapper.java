package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.notification.MailServer;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MailServerJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target = "type", expression = "java(MailServerTypeJpa.valueOf(mailServer.getType().name()))")
    @Mapping(target = "state", expression = "java(MailServerStateJpa.valueOf(mailServer.getState().name()))")
    @Mapping(target =  "protocol", source = "protocol.text.value")
    @Mapping(target =  "host", source = "host.text.value")
    @Mapping(target =  "port", source = "port.value")
    @Mapping(target =  "creationAt", source = "creationAt")
    @Mapping(target =  "confirmedAt", expression = "java(mailServer.getConfirmedAt() == null? null: mailServer.getConfirmedAt())")
    @Mapping(target = "username", expression = "java(mailServer.getUsername() == null || mailServer.getUsername().text().value().isEmpty()? null: mailServer.getUsername().text().value())")
    @Mapping(target = "password", expression = "java(mailServer.getPassword() == null || mailServer.getPassword().text().value().isEmpty()? null: mailServer.getPassword().text().value())")
    @Mapping(target = "useSSL", expression = "java(mailServer.getUseSSL() == null? null: mailServer.getUseSSL().value())")
    @Mapping(target = "useAuth", expression = "java(mailServer.getUseAuth() == null? null: mailServer.getUseAuth().value())")
    @Mapping(target = "active", source = "active.value")
    @Nonnull MailServerJpa toMailServerJpa(@Nonnull MailServer mailServer);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source =  "id", target = "id.value")
    @Mapping(source =  "name", target = "name.text.value")
    @Mapping(source =  "from", target = "from.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailServerType.valueOf(mailServerJpa.getType().name()))", target = "type")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailServerState.valueOf(mailServerJpa.getState().name()))", target = "state")
    @Mapping(source =  "protocol", target = "protocol.text.value")
    @Mapping(source =  "host", target = "host.text.value")
    @Mapping(source =  "port", target = "port.value")
    @Mapping(source =  "creationAt", target = "creationAt")
    @Mapping(expression = "java(mailServerJpa.getConfirmedAt() == null? null: mailServerJpa.getConfirmedAt())", target =  "confirmedAt")
    @Mapping(expression = "java(mailServerJpa.getUsername() == null || mailServerJpa.getUsername().isEmpty()? null: cm.xenonbyte.gestitre.domain.notification.vo.Username.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(mailServerJpa.getUsername())))", target = "username")
    @Mapping(expression = "java(mailServerJpa.getPassword() == null || mailServerJpa.getPassword().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Password.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(mailServerJpa.getPassword())))", target = "password")
    @Mapping(expression = "java(mailServerJpa.getUseSSL() == null? null: cm.xenonbyte.gestitre.domain.notification.vo.UseSSL.with(mailServerJpa.getUseSSL()))", target = "useSSL")
    @Mapping(expression = "java(mailServerJpa.getUseAuth() == null? null: cm.xenonbyte.gestitre.domain.notification.vo.UseAuth.with(mailServerJpa.getUseAuth()))", target = "useAuth")
    @Mapping(source = "active", target = "active.value")
    @Nonnull MailServer toMailServer(@Nonnull MailServerJpa mailServerJpa);

}
