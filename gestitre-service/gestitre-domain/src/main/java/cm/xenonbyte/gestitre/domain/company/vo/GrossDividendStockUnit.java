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
public record GrossDividendStockUnit(Money amount) {
    public GrossDividendStockUnit(@Nonnull Money amount) {
        this.amount = Objects.requireNonNull(amount);
    }

    @Nonnull
    public static GrossDividendStockUnit of(Money grossDividendStockUnit) {
        Assert.field("Gross dividend stock unit", grossDividendStockUnit)
                .notNull()
                .notNull(grossDividendStockUnit.amount())
                .notNull(grossDividendStockUnit.amount())
                .notPositive(grossDividendStockUnit.amount());
        return new GrossDividendStockUnit(grossDividendStockUnit);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GrossDividendStockUnit that = (GrossDividendStockUnit) object;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
