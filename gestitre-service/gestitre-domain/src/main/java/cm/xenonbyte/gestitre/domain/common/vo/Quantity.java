package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.math.BigInteger;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Quantity(BigInteger value) {
    public Quantity(@Nonnull BigInteger value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return Objects.equals(value, quantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Nonnull
    public static Quantity of(BigInteger value) {
        Assert.field("Quantity", value)
                .notNull()
                .notNull(value)
                .notPositive(value.intValue());
        return new Quantity(value);
    }


}
