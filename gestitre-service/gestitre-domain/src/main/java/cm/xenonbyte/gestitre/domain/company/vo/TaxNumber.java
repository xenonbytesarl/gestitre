package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record TaxNumber(Text text) {
    public TaxNumber(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static TaxNumber of(Text taxNumber) {
        Assert.field("Tax Number", taxNumber)
                .notNull()
                .notNull(taxNumber.value())
                .notEmpty(taxNumber.value());
        return new TaxNumber(taxNumber);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaxNumber taxNumber = (TaxNumber) object;
        return Objects.equals(text, taxNumber.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
