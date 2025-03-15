package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.FindByIdMailTemplateViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailTemplatePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
public interface MailTemplateApplicationAdapter {
    @Nonnull @Valid CreateMailTemplateViewResponse createMailTemplate(@Nonnull @Valid CreateMailTemplateViewRequest createMailTemplateViewRequest);
    @Nonnull @Valid
    UpdateMailTemplateViewResponse updateMailTemplate(@Nonnull UUID mailTemplateId, @Nonnull @Valid UpdateMailTemplateViewRequest updateMailTemplateViewRequest);
    @Nonnull @Valid FindByIdMailTemplateViewResponse findMailTemplateById(@Nonnull UUID mailTemplateId);
    @Nonnull @Valid SearchMailTemplatePageInfoViewResponse searchMailTemplates(@Nonnull Integer page, @Nonnull Integer size, @Nonnull String field, @Nonnull String direction, @Nonnull String keyword);
}
