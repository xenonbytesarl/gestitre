package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.ConfirmMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.ConfirmedMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.FindByIdMailServerViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailServerPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailServerViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerConfirmedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailServerService;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerId;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

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
    @Transactional
    public CreateMailServerViewResponse createMailServer(@Nonnull CreateMailServerViewRequest createMailServerViewRequest) {
        MailServerCreatedEvent mailServerCreatedEvent = mailServerService.createMailServer(
                mailServerViewMapper.toCreateMailServer(createMailServerViewRequest));
        return mailServerViewMapper.toCreateMailServerViewResponse(mailServerCreatedEvent.getMailServer());
    }

    @Nonnull
    @Override
    public UpdateMailServerViewResponse updateMailServer(@Nonnull UUID mailServerId, @Nonnull UpdateMailServerViewRequest updateMailServerViewRequest) {
        MailServerUpdatedEvent mailServerUpdatedEvent = mailServerService.updateMailServer(
                new MailServerId(mailServerId), mailServerViewMapper.toUpdateMailServer(updateMailServerViewRequest)
        );
        return mailServerViewMapper.toUpdateMailServerViewResponse(mailServerUpdatedEvent.getMailServer());
    }

    @Nonnull
    @Override
    public ConfirmedMailServerViewResponse confirmMailServer(@Nonnull UUID mailServerId, @Nonnull ConfirmMailServerViewRequest confirmMailServerViewRequest) {
        MailServerConfirmedEvent mailServerConfirmedEvent = mailServerService.confirmMailServer(new MailServerId(mailServerId), Code.of(Text.of(confirmMailServerViewRequest.code())));
        return mailServerViewMapper.toConfirmedMailServerViewResponse(mailServerConfirmedEvent.getMailServer());
    }

    @Nonnull
    @Override
    public FindByIdMailServerViewResponse findMailServerById(@Nonnull UUID mailServerId) {
        return mailServerViewMapper.toFindByIdMailServerResponse(
                mailServerService.findMailServerById(new MailServerId(mailServerId))
        );
    }

    @Nonnull
    @Override
    public SearchMailServerPageInfoViewResponse searchMailServers(@Nonnull Integer page, @Nonnull Integer size, String field, String direction, String keyword) {
        return mailServerViewMapper.toSearchMailServerPageInfoViewResponse(
                mailServerService.searchMailServers(
                        PageInfoPage.of(page),
                        PageInfoSize.of(size),
                        PageInfoField.of(Text.of(field)),
                        PageInfoDirection.valueOf(direction),
                        Keyword.of(Text.of(keyword))
                )
        );
    }
}
