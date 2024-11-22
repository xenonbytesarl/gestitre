package cm.xenonbyte.gestitre.domain.security.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public record FailedLoginAttempt(Long value) {
    public FailedLoginAttempt(@Nonnull Long value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static FailedLoginAttempt of(Long value) {
        Assert.field("Failed Login Attempt", value)
                .notNull()
                .notNull(value)
                .notPositive(value);
        return new FailedLoginAttempt(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FailedLoginAttempt failedLoginAttempt = (FailedLoginAttempt) object;
        return Objects.equals(value, failedLoginAttempt.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
