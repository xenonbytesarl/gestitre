package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Quantity;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.vo.MailId;
import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailState;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import cm.xenonbyte.gestitre.domain.notification.vo.Message;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class Mail extends BaseEntity<MailId> {
    private final Text subject;
    private final Email to;
    private final MailTemplateType type;
    private final MailTemplateId mailTemplateId;
    private final MailServerId mailServerId;
    private Message message;
    private Email cc;
    private MailState state;
    private ZonedDateTime createdAt;
    private ZonedDateTime sendAt;
    private Quantity attemptToSend;
    private Map<Text, Text> attributes;


    public Mail(Text subject, Email to, MailTemplateType type, MailTemplateId mailTemplateId, MailServerId mailServerId) {
        this.subject = Objects.requireNonNull(subject);
        this.to = Objects.requireNonNull(to);
        this.type = Objects.requireNonNull(type);
        this.mailTemplateId = Objects.requireNonNull(mailTemplateId);
        this.mailServerId = Objects.requireNonNull(mailServerId);
    }
}
