package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Money;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record NominalValue(Money amount) {
    public NominalValue(@Nonnull Money amount) {
        this.amount = Objects.requireNonNull(amount);
    }

    @Nonnull
    public static NominalValue of(Money nominalValue) {
        Assert.field("Nominal Value", nominalValue)
                .notNull()
                .notNull(nominalValue.amount())
                .notNull(nominalValue.amount())
                .notPositive(nominalValue.amount());
        return new NominalValue(nominalValue);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NominalValue that = (NominalValue) object;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
