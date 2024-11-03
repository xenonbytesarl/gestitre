package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Money;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Capitalization(NominalValue nominalValue, Quantity stockQuantity) {
    public Capitalization(@Nonnull NominalValue nominalValue, @Nonnull Quantity stockQuantity) {
        this.nominalValue = Objects.requireNonNull(nominalValue);
        this.stockQuantity = Objects.requireNonNull(stockQuantity);
    }

    @Nonnull
    public static Capitalization of(NominalValue nominalValue, Quantity stockQuantity) {
        Assert.field("Nominative value", nominalValue)
                .notNull()
                .notNull(nominalValue.amount());

        Assert.field("Stock", stockQuantity)
                .notNull()
                .notNull(stockQuantity.value());
        return new Capitalization(nominalValue, stockQuantity);
    }

    public Money getAmount() {
        return new Money(nominalValue.amount().value().multiply(BigDecimal.valueOf(stockQuantity.value())));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Capitalization that = (Capitalization) object;
        return Objects.equals(stockQuantity, that.stockQuantity) && Objects.equals(nominalValue, that.nominalValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominalValue, stockQuantity);
    }
}
