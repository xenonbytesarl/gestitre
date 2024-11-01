package cm.xenonbyte.gestitre.domain.company.vo.address;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Fax(Text text) {
    public Fax(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Fax of(Text fax) {
        Assert.field("Fax", fax)
                .notNull()
                .notNull(fax.value())
                .notEmpty(fax.value())
                .notNumberValue(fax.value());
        return new Fax(fax);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Fax fax = (Fax) object;
        return Objects.equals(text, fax.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
