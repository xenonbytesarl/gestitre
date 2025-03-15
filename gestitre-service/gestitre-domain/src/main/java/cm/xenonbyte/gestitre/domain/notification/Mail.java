package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Quantity;
import cm.xenonbyte.gestitre.domain.common.vo.Subject;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.vo.MailId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailState;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import cm.xenonbyte.gestitre.domain.notification.vo.Message;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class Mail extends BaseEntity<MailId> {
    public static final String MAIL_DEFAULT_MFA_SUBJECT = "Mail.1";
    public static final String MAIL_DEFAULT_ACTIVATE_ACCOUNT_SUBJECT = "Mail.2";
    public static final String MAIL_DEFAULT_RESET_PASSWORD_SUBJECT = "Mail.3";
    public static final String MAIL_DEFAULT_VERIFY_MAIL_SERVER_SUBJECT= "Mail.4";
    public static final BigInteger MAX_ATTEMPT_TO_SEND = BigInteger.valueOf(5);

    private final Subject subject;
    private final Email to;
    private final MailTemplateType type;
    private Email from;
    private MailServerId mailServerId;
    private MailTemplateId mailTemplateId;
    private Message message;
    private Email cc;
    private MailState state;
    private ZonedDateTime createdAt;
    private ZonedDateTime sendAt;
    private Quantity attemptToSend;
    private Map<Text, Text> attributes;


    public Mail(Subject subject, Email to, MailTemplateType type) {
        this.subject = Objects.requireNonNull(subject);
        this.to = Objects.requireNonNull(to);
        this.type = Objects.requireNonNull(type);
    }

    private Mail(Builder builder) {
        setId(builder.id);
        subject = builder.subject;
        to = builder.to;
        from = builder.from;
        type = builder.type;
        mailServerId = builder.mailServerId;
        mailTemplateId = builder.mailTemplateId;
        message = builder.message;
        cc = builder.cc;
        state = builder.state;
        createdAt = builder.createdAt;
        sendAt = builder.sendAt;
        attemptToSend = builder.attemptToSend;
        attributes = builder.attributes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Subject getSubject() {
        return subject;
    }

    public Email getTo() {
        return to;
    }

    public MailTemplateType getType() {
        return type;
    }

    public MailTemplateId getMailTemplateId() {
        return mailTemplateId;
    }

    public MailServerId getMailServerId() {
        return mailServerId;
    }

    public Message getMessage() {
        return message;
    }

    public Email getCc() {
        return cc;
    }

    public MailState getState() {
        return state;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getSendAt() {
        return sendAt;
    }

    public Quantity getAttemptToSend() {
        return attemptToSend;
    }

    public Map<Text, Text> getAttributes() {
        return attributes;
    }

    public Email getFrom() {
        return from;
    }

    public void validateMandatoryFields() {
        Assert.field("Subject", subject)
                .notNull();

        Assert.field("", to)
                .notNull();

        Assert.field("Type", type)
                .notNull();

    }

    public void initializeWithDefaults(MailTemplateId mailTemplateId, MailServerId mailserverId, Email from) {
        setId( new MailId(UUID.randomUUID()));
        createdAt = ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId());
        if(this.mailServerId == null) {
            this.mailServerId = mailserverId;
        }
        this.from = from;
        this.mailTemplateId = mailTemplateId;
        state = MailState.SENDING;
    }

    public void fail() {
        if(attemptToSend != null && attemptToSend.value().compareTo(MAX_ATTEMPT_TO_SEND) >= 0 ) {
            state = MailState.ERROR;
        }
    }

    public void success() {
        state = MailState.SEND;
    }

    public void incrementAttempt() {
        attemptToSend = new Quantity(attemptToSend == null? BigInteger.ONE: attemptToSend.value().add(BigInteger.ONE));
    }


    public static final class Builder {
        private MailId id;
        private Subject subject;
        private Email to;
        private Email from;
        private MailTemplateType type;
        private MailServerId mailServerId;
        private MailTemplateId mailTemplateId;
        private Message message;
        private Email cc;
        private MailState state;
        private ZonedDateTime createdAt;
        private ZonedDateTime sendAt;
        private Quantity attemptToSend;
        private Map<Text, Text> attributes;

        private Builder() {
        }

        public Builder id(MailId val) {
            id = val;
            return this;
        }

        public Builder subject(Subject val) {
            subject = val;
            return this;
        }

        public Builder to(Email val) {
            to = val;
            return this;
        }

        public Builder from(Email val) {
            from = val;
            return this;
        }

        public Builder type(MailTemplateType val) {
            type = val;
            return this;
        }

        public Builder mailServerId(MailServerId val) {
            mailServerId = val;
            return this;
        }

        public Builder mailTemplateId(MailTemplateId val) {
            mailTemplateId = val;
            return this;
        }

        public Builder message(Message val) {
            message = val;
            return this;
        }

        public Builder cc(Email val) {
            cc = val;
            return this;
        }

        public Builder state(MailState val) {
            state = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder sendAt(ZonedDateTime val) {
            sendAt = val;
            return this;
        }

        public Builder attemptToSend(Quantity val) {
            attemptToSend = val;
            return this;
        }

        public Builder attributes(Map<Text, Text> val) {
            attributes = val;
            return this;
        }

        public Mail build() {
            return new Mail(this);
        }
    }
}
