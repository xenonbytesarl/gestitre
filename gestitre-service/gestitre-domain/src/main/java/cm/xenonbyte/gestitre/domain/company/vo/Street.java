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
public record Street(Text text) {
    public Street(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Street of(Text street) {
        Assert.field("Street", street)
                .notNull()
                .notNull(street.value())
                .notEmpty(street.value());
        return new Street(street);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Street street = (Street) object;
        return Objects.equals(text, street.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
