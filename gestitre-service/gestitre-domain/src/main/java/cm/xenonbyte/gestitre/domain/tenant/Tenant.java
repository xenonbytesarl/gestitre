package cm.xenonbyte.gestitre.domain.tenant;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;

import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class Tenant extends BaseEntity<TenantId> {
    private final Code code;
    private final Name name;
    private Active active;

    public Tenant(Name name, Code code) {
        this.name = Objects.requireNonNull(name);
        this.code = Objects.requireNonNull(code);
    }

    private Tenant(Builder builder) {
        setId(builder.id);
        name = builder.name;
        code = builder.code;
        active = builder.active;
    }

    public static Tenant of(Name name, Code code) {
        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value());
        Assert.field("Code", code)
                .notNull()
                .notNull(code.text())
                .notNull(code.text().value());
        return new Tenant(name, code);
    }

    public Name getName() {
        return name;
    }

    public Code getCode() {
        return code;
    }

    public Active getActive() {
        return active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeDefaults() {
        setId(new TenantId(UUID.randomUUID()));
        this.active = Active.with(true);
    }

    public void validateMandatoryFields() {
        Assert.field("name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value());
        Assert.field("Code", code)
                .notNull()
                .notNull(code.text())
                .notNull(code.text().value());
    }


    public static final class Builder {
        private TenantId id;
        private Name name;
        private Code code;
        private Active active;

        private Builder() {
        }

        public Builder id(TenantId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder code(Code val) {
            code = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public Tenant build() {
            return new Tenant(this);
        }
    }
}
