package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record Active(Boolean value) {


    public Active(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static Active with(Boolean value) {
        Assert.field("Active", value)
                .notNull();
        return new Active(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Active active = (Active) object;
        return Objects.equals(value, active.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
