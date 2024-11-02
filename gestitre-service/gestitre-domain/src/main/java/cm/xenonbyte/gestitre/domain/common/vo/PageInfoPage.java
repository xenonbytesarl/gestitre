package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record PageInfoPage(Integer value) {
    public PageInfoPage(@Nonnull Integer value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static PageInfoPage of(Integer value) {
        Assert.field("Page", value)
                .notNull()
                .notNull(value)
                .notPositive(value);
        return new PageInfoPage(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PageInfoPage quantity = (PageInfoPage) object;
        return Objects.equals(value, quantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
