package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public final class MailTemplate extends BaseEntity<MailTemplateId> {
    private final Name name;
    private final MailTemplateType type;
    private Active active;

    public MailTemplate(Name name, MailTemplateType type) {
        this.name = name;
        this.type = type;
    }

    private MailTemplate(Builder builder) {
        setId(builder.id);
        name = builder.name;
        type = builder.type;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateMandatoryFields() {
        Assert.field("Name", name)
                .notNull();
        Assert.field("Type", type)
                .notNull();
    }

    public void initializeDefaults() {
        setId(new MailTemplateId(UUID.randomUUID()));
        if(active == null) {
            active = Active.with(true);
        }
    }

    public Name getName() {
        return name;
    }

    public MailTemplateType getType() {
        return type;
    }

    public Active getActive() {
        return active;
    }

    public static final class Builder {
        private MailTemplateId id;
        private Name name;
        private MailTemplateType type;
        public Active active;

        private Builder() {
        }

        public Builder id(MailTemplateId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder type(MailTemplateType val) {
            type = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public MailTemplate build() {
            return new MailTemplate(this);
        }
    }
}
