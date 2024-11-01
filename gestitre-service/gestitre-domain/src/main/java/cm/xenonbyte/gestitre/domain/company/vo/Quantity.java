package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Quantity(Long value) {
    public Quantity(@Nonnull Long value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static Quantity of(Long value) {
        Assert.field("Quantity", value)
                .notNull()
                .notNull(value)
                .notPositive(value);
        return new Quantity(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Quantity quantity = (Quantity) object;
        return Objects.equals(value, quantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
