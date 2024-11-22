package cm.xenonbyte.gestitre.domain.security.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public record UseMfa(Boolean value) {
    public UseMfa(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static UseMfa with(Boolean value) {
        Assert.field("Use mfa", value)
                .notNull();
        return new UseMfa(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UseMfa that = (UseMfa) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
