package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public class Role extends BaseEntity<RoleId> {

    private final Name name;
    private final Set<Permission> permissions;
    private Active active;

    public Role(@Nonnull Name name, @Nonnull Set<Permission> permissions) {
        this.name = Objects.requireNonNull(name);
        this.permissions = Objects.requireNonNull(permissions);
    }

    private Role(Builder builder) {
        setId(builder.id);
        name = builder.name;
        permissions = builder.permissions;
        active = builder.active;
    }

    public Name getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Active getActive() {
        return active;
    }

    @Nonnull
    public static Role of(Name name, Set<Permission> permissions) {
        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notEmpty(name.text().value());
        Assert.field("Permissions", permissions)
                .notNull()
                .notPositive(permissions.size());
        return new Role(name, permissions);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeDefaults() {
        setId(new RoleId(UUID.randomUUID()));
        this.active = Active.with(true);
    }


    public static final class Builder {
        private RoleId id;
        private Name name;
        private Set<Permission> permissions;
        private Active active;

        private Builder() {
        }

        public Builder id(RoleId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder permissions(Set<Permission> val) {
            permissions = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
