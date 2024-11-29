package cm.xenonbyte.gestitre.domain.notification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public record UseSSL(Boolean value) {
    public UseSSL(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static UseSSL with(Boolean value) {
        Assert.field("UseSSL", value)
                .notNull();
        return new UseSSL(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UseSSL useSSL = (UseSSL) object;
        return Objects.equals(value, useSSL.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
