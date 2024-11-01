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
public record Capitalization(Money amount, Quantity stockQuantity) {
    public Capitalization(@Nonnull Money amount, @Nonnull Quantity stockQuantity) {
        this.amount = Objects.requireNonNull(amount);
        this.stockQuantity = Objects.requireNonNull(stockQuantity);
    }

    @Nonnull
    public static Capitalization of(Money capitalization, Quantity stockQuantity) {
        Assert.field("Capitalization", capitalization)
                .notNull()
                .notNull(capitalization.amount())
                .notNull(capitalization.amount())
                .notPositive(capitalization.amount());
        Assert.field("Stock", stockQuantity)
                .notNull()
                .notNull(capitalization.amount());
        return new Capitalization(capitalization, stockQuantity);
    }

    public Money getAmount() {
        return new Money(amount.amount().multiply(BigDecimal.valueOf(stockQuantity.value())));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Capitalization that = (Capitalization) object;
        return Objects.equals(amount, that.amount) && Objects.equals(stockQuantity, that.stockQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, stockQuantity);
    }
}
