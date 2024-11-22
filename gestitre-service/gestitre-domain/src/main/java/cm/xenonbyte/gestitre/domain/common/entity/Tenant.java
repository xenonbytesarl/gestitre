package cm.xenonbyte.gestitre.domain.common.entity;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
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
    private final Name name;
    private Active active;

    public Tenant(Name name) {
        this.name = Objects.requireNonNull(name);
    }

    private Tenant(Builder builder) {
        setId(builder.id);
        name = builder.name;
        active = builder.active;
    }

    public static Tenant of(Name name) {
        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value());
        return new Tenant(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeDefaults() {
        setId(new TenantId(UUID.randomUUID()));
        this.active = Active.with(true);
    }

    public Name getName() {
        return name;
    }


    public static final class Builder {
        private TenantId id;
        private Name name;
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

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public Tenant build() {
            return new Tenant(this);
        }
    }
}
