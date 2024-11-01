package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 20/08/2024
 */
public record Money(BigDecimal amount) {

    public static final Money ZERO = Money.of(BigDecimal.ZERO);

    public Money(@Nonnull BigDecimal amount) {
        this.amount = Objects.requireNonNull(amount);
    }

    public static Money of(@Nonnull BigDecimal amount) {
        Assert.field("Money amount", amount)
                .notNull()
                .notPositive(amount);
        return new Money(amount);
    }

    public boolean isNegative() {
        return lessThan(ZERO);
    }

    private Boolean lessThan(Money money) {
        return amount.compareTo(money.amount) < 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Money money = (Money) object;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
