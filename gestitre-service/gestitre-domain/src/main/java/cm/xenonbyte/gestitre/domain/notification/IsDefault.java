package cm.xenonbyte.gestitre.domain.notification;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 12/03/2025
 */
public record IsDefault(Boolean value) {
    public IsDefault(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static IsDefault with(Boolean value) {
        Assert.field("IsDefault", value)
                .notNull();
        return new IsDefault(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        IsDefault isDefault = (IsDefault) object;
        return Objects.equals(value, isDefault.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
