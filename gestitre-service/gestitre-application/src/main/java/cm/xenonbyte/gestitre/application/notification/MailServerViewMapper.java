package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.ConfirmedMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.FindByIdMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailServerPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailsServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
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
 * @since 02/12/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MailServerViewMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "from", target = "from.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailServerType.valueOf(createMailServerViewRequest.getType().name()))", target = "type")
    @Mapping(source = "protocol", target = "protocol.text.value")
    @Mapping(source = "host", target = "host.text.value")
    @Mapping(source = "port", target = "port.value")
    @Mapping(expression = "java(createMailServerViewRequest.getUsername() == null || createMailServerViewRequest.getUsername().isEmpty()? null: cm.xenonbyte.gestitre.domain.notification.vo.Username.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createMailServerViewRequest.getUsername())))", target = "username")
    @Mapping(expression = "java(createMailServerViewRequest.getPassword() == null || createMailServerViewRequest.getPassword().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Password.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createMailServerViewRequest.getPassword())))", target = "password")
    @Mapping(expression = "java(createMailServerViewRequest.getUseSSL() == null? null: cm.xenonbyte.gestitre.domain.notification.vo.UseSSL.with(createMailServerViewRequest.getUseSSL()))", target = "useSSL")
    @Mapping(expression = "java(createMailServerViewRequest.getUseAuth() == null? null: cm.xenonbyte.gestitre.domain.notification.vo.UseAuth.with(createMailServerViewRequest.getUseAuth()))", target = "useAuth")
    @Nonnull MailServer toCreateMailServer(@Nonnull @Valid CreateMailServerViewRequest createMailServerViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerTypeView.valueOf(mailServer.getType().name()))")
    @Mapping(target = "state", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerStateView.valueOf(mailServer.getState().name()))")
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
    @Nonnull @Valid CreateMailServerViewResponse toCreateMailServerViewResponse(@Nonnull MailServer mailServer);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source =  "id", target = "id.value")
    @Mapping(source = "name", target = "name.text.value")
    @Mapping(source = "from", target = "from.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailServerType.valueOf(updateMailServerViewRequest.getType().name()))", target = "type")
    @Mapping(source = "protocol", target = "protocol.text.value")
    @Mapping(source = "host", target = "host.text.value")
    @Mapping(source = "port", target = "port.value")
    @Mapping(source =  "creationAt", target = "creationAt")
    @Mapping(expression = "java(updateMailServerViewRequest.getConfirmedAt() == null? null: updateMailServerViewRequest.getConfirmedAt())", target =  "confirmedAt")
    @Mapping(expression = "java(updateMailServerViewRequest.getUsername() == null || updateMailServerViewRequest.getUsername().isEmpty()? null: cm.xenonbyte.gestitre.domain.notification.vo.Username.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateMailServerViewRequest.getUsername())))", target = "username")
    @Mapping(expression = "java(updateMailServerViewRequest.getPassword() == null || updateMailServerViewRequest.getPassword().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Password.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateMailServerViewRequest.getPassword())))", target = "password")
    @Mapping(expression = "java(updateMailServerViewRequest.getUseSSL() == null? null: cm.xenonbyte.gestitre.domain.notification.vo.UseSSL.with(updateMailServerViewRequest.getUseSSL()))", target = "useSSL")
    @Mapping(expression = "java(updateMailServerViewRequest.getUseAuth() == null? null: cm.xenonbyte.gestitre.domain.notification.vo.UseAuth.with(updateMailServerViewRequest.getUseAuth()))", target = "useAuth")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.notification.vo.MailServerState.valueOf(updateMailServerViewRequest.getState().name()))", target = "state")
    @Mapping(source = "active", target = "active.value")
    @Nonnull MailServer toUpdateMailServer(@Nonnull @Valid UpdateMailServerViewRequest updateMailServerViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerTypeView.valueOf(mailServer.getType().name()))")
    @Mapping(target = "state", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerStateView.valueOf(mailServer.getState().name()))")
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
    @Nonnull @Valid UpdateMailServerViewResponse toUpdateMailServerViewResponse(@Nonnull MailServer mailServer);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerTypeView.valueOf(mailServer.getType().name()))")
    @Mapping(target = "state", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerStateView.valueOf(mailServer.getState().name()))")
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
    @Nonnull @Valid ConfirmedMailServerViewResponse toConfirmedMailServerViewResponse(@Nonnull MailServer mailServer);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerTypeView.valueOf(mailServer.getType().name()))")
    @Mapping(target = "state", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerStateView.valueOf(mailServer.getState().name()))")
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
    @Nonnull @Valid FindByIdMailServerViewResponse toFindByIdMailServerResponse(@Nonnull MailServer mailServer);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchMailsServerViewResponses", target = "elements")
    @Nonnull @Valid SearchMailServerPageInfoViewResponse toSearchMailServerPageInfoViewResponse(@Nonnull PageInfo<MailServer> mailServerPageInfo);

    @Named("toSearchMailsServerViewResponses")
    @Nonnull @Valid List<SearchMailsServerViewResponse> toSearchMailsServerViewResponses(@Nonnull List<MailServer> mailServers);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target =  "id", source = "id.value")
    @Mapping(target =  "name", source = "name.text.value")
    @Mapping(target =  "from", source = "from.text.value")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerTypeView.valueOf(mailServer.getType().name()))")
    @Mapping(target = "state", expression = "java(cm.xenonbyte.gestitre.application.notification.dto.MailServerStateView.valueOf(mailServer.getState().name()))")
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
    @Nonnull @Valid SearchMailsServerViewResponse toSearchMailsServerViewResponse(@Nonnull MailServer mailServer);


}
