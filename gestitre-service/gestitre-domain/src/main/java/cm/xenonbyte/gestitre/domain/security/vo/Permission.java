package cm.xenonbyte.gestitre.domain.security.vo;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public class Permission extends BaseEntity<PermissionId> {
    private final Name name;

    public Permission(@Nonnull Name name) {
        this.name = Objects.requireNonNull(name);
    }

    private Permission(Builder builder) {
        setId(builder.id);
        name = builder.name;
    }

    public static Permission of(@Nonnull Name name) {
        Assert.field("name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value())
                .notEmpty(name.text().value());
        return new Permission(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeDefaults() {
        setId(new PermissionId(UUID.randomUUID()));
    }

    public Name getName() {
        return name;
    }


    public static final class Builder {
        private PermissionId id;
        private Name name;

        private Builder() {
        }

        public Builder id(PermissionId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Permission build() {
            return new Permission(this);
        }
    }
}
