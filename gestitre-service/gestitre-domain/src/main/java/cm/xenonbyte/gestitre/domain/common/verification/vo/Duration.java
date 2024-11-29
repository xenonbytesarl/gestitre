package cm.xenonbyte.gestitre.domain.common.verification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public record Duration(Long value) {
    public Duration(@Nonnull Long value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static Duration of(Long duration) {
        Assert.field("Duration", duration)
                .notNull()
                .notNull(duration);
        return new Duration(duration);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Duration duration = (Duration) object;
        return Objects.equals(value, duration.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
