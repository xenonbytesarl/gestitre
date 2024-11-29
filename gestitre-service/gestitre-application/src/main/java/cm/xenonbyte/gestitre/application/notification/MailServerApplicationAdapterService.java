package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewResponse;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailServerService;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Slf4j
@ApplicationScoped
public final class MailServerApplicationAdapterService implements MailServerApplicationAdapter {

    private final MailServerService mailServerService;
    private final MailServerViewMapper mailServerViewMapper;

    public MailServerApplicationAdapterService(
            @Nonnull MailServerService mailServerService,
            @Nonnull MailServerViewMapper mailServerViewMapper) {
        this.mailServerService = Objects.requireNonNull(mailServerService);
        this.mailServerViewMapper = Objects.requireNonNull(mailServerViewMapper);
    }

    @Nonnull
    @Override
    public CreateMailServerViewResponse createMailServer(@Nonnull CreateMailServerViewRequest createMailServerViewRequest) {
        MailServerCreatedEvent mailServerCreatedEvent = mailServerService.createMailServer(
                mailServerViewMapper.toCreateMailServer(createMailServerViewRequest));
        return mailServerViewMapper.toCreateMailServerViewResponse(mailServerCreatedEvent.getMailServer());
    }
}
