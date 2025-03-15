package cm.xenonbyte.gestitre.domain.notification.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.Mail;
import cm.xenonbyte.gestitre.domain.notification.vo.MailId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailState;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public interface MailRepository {
    @Nonnull Mail save(@Nonnull Mail mail);

    Optional<Mail> findById(@Nonnull MailId mailId);

    @Nonnull PageInfo<Mail> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword);

    List<Mail> findByState(@Nonnull MailState mailState);

    @Nonnull Mail update(@Nonnull MailId mailId, @Nonnull Mail mail);
}
