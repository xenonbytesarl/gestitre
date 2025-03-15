package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.ConfirmMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.ConfirmedMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.FindByIdMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailServerPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
public interface MailServerApplicationAdapter {
    @Nonnull @Valid CreateMailServerViewResponse createMailServer(@Nonnull @Valid CreateMailServerViewRequest createMailServerViewRequest);
    @Nonnull @Valid UpdateMailServerViewResponse updateMailServer(@Nonnull UUID mailServerId, @Nonnull @Valid UpdateMailServerViewRequest updateMailServerViewRequest);
    @Nonnull @Valid ConfirmedMailServerViewResponse confirmMailServer(@Nonnull UUID mailServerId, @Nonnull ConfirmMailServerViewRequest confirmMailServerViewRequest);
    @Nonnull @Valid FindByIdMailServerViewResponse findMailServerById(@Nonnull UUID mailServerId);
    @Nonnull @Valid SearchMailServerPageInfoViewResponse searchMailServers(@Nonnull Integer page, @Nonnull Integer size, @Nonnull String field, @Nonnull String direction, @Nonnull String keyword);
}
