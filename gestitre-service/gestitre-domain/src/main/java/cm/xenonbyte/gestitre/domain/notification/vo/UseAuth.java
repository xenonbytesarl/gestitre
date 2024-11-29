package cm.xenonbyte.gestitre.domain.notification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public record UseAuth(Boolean value) {
    public UseAuth(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static UseAuth with(Boolean value) {
        Assert.field("Use Auth", value)
                .notNull();
        return new UseAuth(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UseAuth useAuth = (UseAuth) object;
        return Objects.equals(value, useAuth.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
