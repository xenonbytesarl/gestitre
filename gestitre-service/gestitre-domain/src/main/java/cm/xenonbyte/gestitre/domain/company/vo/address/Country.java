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
public record Country(Text text) {
    public Country(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Country of(Text country) {
        Assert.field("Country", country)
                .notNull()
                .notNull(country.value())
                .notEmpty(country.value());
        return new Country(country);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Country country = (Country) object;
        return Objects.equals(text, country.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
