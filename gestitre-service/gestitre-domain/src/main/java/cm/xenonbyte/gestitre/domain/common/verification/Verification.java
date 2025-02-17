package cm.xenonbyte.gestitre.domain.common.verification;


import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationId;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationStatus;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class Verification extends BaseEntity<VerificationId> {
    private final VerificationType type;
    private final Email email;
    private final Duration duration;
    private UserId userId;
    private MailServerId mailServerId;
    private ZonedDateTime expiredAt;
    private ZonedDateTime createdAt;
    private VerificationStatus status;
    private Code code;
    private Url url;

    public Verification(VerificationType type, Duration duration, Email email) {
        this.type = Objects.requireNonNull(type);
        this.duration = Objects.requireNonNull(duration);
        this.email = Objects.requireNonNull(email);
    }

    private Verification(Builder builder) {
        setId(builder.id);
        type = builder.type;
        expiredAt = builder.expiredAt;
        duration = builder.duration;
        userId = builder.userId;
        mailServerId = builder.mailServerId;
        email = builder.email;
        createdAt = builder.createdAt;
        status = builder.status;
        code = builder.code;
        url = builder.url;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateMandatoryFields() {
        Assert.field("Type", type)
                .notNull();

        Assert.field("Email", email)
                .notNull();

        Assert.field("Duration", duration)
                .notNull()
                .notNull(duration.value());

        if(type.equals(VerificationType.MAIL_SERVER)) {
            Assert.field("Mail Server ID", mailServerId)
                    .notNull();
        } else {
            Assert.field("User ID", userId)
                    .notNull();
        }
    }

    public void initializeDefaults() {
        setId(new VerificationId(UUID.randomUUID()));
        status = VerificationStatus.NOT_VERIFIED;
        createdAt = ZonedDateTime.now();
        expiredAt = ZonedDateTime.now().plusSeconds(duration.value());
    }

    public void initializeDefaultsWithUrl(String url, String code) {
        this.url = Url.of(Text.of(url));
        this.code = Code.of(Text.of(code));
        initializeDefaults();
    }

    public void initializeDefaultsWithCode(String code) {
        this.code = Code.of(Text.of(code));
        initializeDefaults();
    }

    public void cancel() {
        status = VerificationStatus.CANCELLED;
    }

    public void verify() {
        status = VerificationStatus.VERIFIED;
    }

    public VerificationType getType() {
        return type;
    }

    public UserId getUserId() {
        return userId;
    }

    public MailServerId getMailServerId() {
        return mailServerId;
    }

    public Duration getDuration() {
        return duration;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public Code getCode() {
        return code;
    }

    public Url getUrl() {
        return url;
    }

    public Email getEmail() {
        return email;
    }


    public static final class Builder {
        private VerificationId id;
        private VerificationType type;
        private ZonedDateTime expiredAt;
        private Duration duration;
        private UserId userId;
        private MailServerId mailServerId;
        private Email email;
        private ZonedDateTime createdAt;
        private VerificationStatus status;
        private Code code;
        private Url url;

        private Builder() {
        }

        public Builder id(VerificationId val) {
            id = val;
            return this;
        }

        public Builder type(VerificationType val) {
            type = val;
            return this;
        }

        public Builder expiredAt(ZonedDateTime val) {
            expiredAt = val;
            return this;
        }

       public Builder duration(Duration val) {
            duration = val;
            return this;
       }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder mailServerId(MailServerId val) {
            mailServerId = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder status(VerificationStatus val) {
            status = val;
            return this;
        }

        public Builder code(Code val) {
            code = val;
            return this;
        }

        public Builder url(Url val) {
            url = val;
            return this;
        }

        public Verification build() {
            return new Verification(this);
        }
    }
}
