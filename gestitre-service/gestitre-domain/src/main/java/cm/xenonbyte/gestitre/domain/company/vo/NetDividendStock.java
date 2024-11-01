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
public record NetDividendStock(Money amount) {
    public NetDividendStock(@Nonnull Money amount) {
        this.amount = Objects.requireNonNull(amount);
    }

    @Nonnull
    public static NetDividendStock of(Money netDividendStock) {
        Assert.field("NominalValue", netDividendStock)
                .notNull()
                .notNull(netDividendStock.amount())
                .notNull(netDividendStock.amount())
                .notPositive(netDividendStock.amount());
        return new NetDividendStock(netDividendStock);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NetDividendStock that = (NetDividendStock) object;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
