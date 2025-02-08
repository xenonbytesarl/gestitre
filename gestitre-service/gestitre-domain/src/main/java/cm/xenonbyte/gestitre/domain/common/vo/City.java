package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record City(Text text) {
    public City(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static City of(Text city) {
        Assert.field("City", city)
                .notNull()
                .notNull(city.value())
                .notEmpty(city.value());
        return new City(city);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        City city = (City) object;
        return Objects.equals(text, city.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
