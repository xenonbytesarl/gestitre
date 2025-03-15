package cm.xenonbyte.gestitre.domain.notification.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.Mail;
import cm.xenonbyte.gestitre.domain.notification.event.MailCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.vo.MailId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public interface MailService {

    @Nonnull MailCreatedEvent createMail(@Nonnull Mail mail);

    @Nonnull Mail findMailById(@Nonnull MailId mailId);

    @Nonnull PageInfo<Mail> searchMail(@Nonnull PageInfoPage page, PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);

    void sendMails();
}
