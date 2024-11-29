package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
public interface MailServerApplicationAdapter {
    @Nonnull @Valid CreateMailServerViewResponse createMailServer(@Nonnull @Valid CreateMailServerViewRequest createMailServerViewRequest);
}
