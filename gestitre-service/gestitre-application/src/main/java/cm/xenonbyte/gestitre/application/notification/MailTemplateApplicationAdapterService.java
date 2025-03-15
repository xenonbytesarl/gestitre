package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.CreateMailTemplateViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.FindByIdMailTemplateViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.SearchMailTemplatePageInfoViewResponse;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewRequest;
import cm.xenonbyte.gestitre.application.notification.dto.UpdateMailTemplateViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailTemplateService;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailTemplateApplicationAdapterService implements MailTemplateApplicationAdapter {

    private final MailTemplateService mailTemplateService;
    private final MailTemplateViewMapper mailTemplateViewMapper;

    public MailTemplateApplicationAdapterService(MailTemplateService mailTemplateService, MailTemplateViewMapper mailTemplateViewMapper) {
        this.mailTemplateService = requireNonNull(mailTemplateService);
        this.mailTemplateViewMapper = requireNonNull(mailTemplateViewMapper);
    }

    @Nonnull
    @Override
    public CreateMailTemplateViewResponse createMailTemplate(@Nonnull CreateMailTemplateViewRequest createMailTemplateViewRequest) {
        MailTemplateCreatedEvent mailTemplateCreatedEvent = mailTemplateService.createMailTemplate(
                mailTemplateViewMapper.toMailTemplate(createMailTemplateViewRequest));
        return mailTemplateViewMapper.toCreateMailTemplateviewResponse(mailTemplateCreatedEvent.getMailTemplate());
    }

    @Nonnull
    @Override
    public UpdateMailTemplateViewResponse updateMailTemplate(@Nonnull UUID mailTemplateId, @Nonnull UpdateMailTemplateViewRequest updateMailTemplateViewRequest) {
        MailTemplateUpdatedEvent mailTemplateUpdatedEvent = mailTemplateService.updateMailTemplate(
                new MailTemplateId(mailTemplateId), mailTemplateViewMapper.toMailTemplate(updateMailTemplateViewRequest));
        return mailTemplateViewMapper.toUpdateMailTemplateviewResponse(mailTemplateUpdatedEvent.getMailTemplate());
    }

    @Nonnull
    @Override
    public FindByIdMailTemplateViewResponse findMailTemplateById(@Nonnull UUID mailTemplateId) {
        return mailTemplateViewMapper.toFindByIdMailTemplateViewResponse(
                mailTemplateService.findMailTemplateById(new MailTemplateId(mailTemplateId))
        );
    }

    @Nonnull
    @Override
    public SearchMailTemplatePageInfoViewResponse searchMailTemplates(@Nonnull Integer page, @Nonnull Integer size, @Nonnull String field, @Nonnull String direction, @Nonnull String keyword) {
        return mailTemplateViewMapper.toSearchMailTemplatePageInfoViewResponse(
                mailTemplateService.searchMailTemplate(
                        PageInfoPage.of(page),
                        PageInfoSize.of(size),
                        PageInfoField.of(Text.of(field)),
                        PageInfoDirection.valueOf(direction),
                        Keyword.of(Text.of(keyword))
                )
        );
    }
}
