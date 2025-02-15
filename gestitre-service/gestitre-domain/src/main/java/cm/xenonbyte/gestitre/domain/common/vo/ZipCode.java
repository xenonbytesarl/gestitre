package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record ZipCode(Text text) {
    public ZipCode(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static ZipCode of(Text zipCode) {
        Assert.field("Zip code", zipCode)
            .notNull()
            .notNull(zipCode.value())
            .notEmpty(zipCode.value())
            .notNumberValue(zipCode.value());
        return new ZipCode(zipCode);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ZipCode zipCode = (ZipCode) object;
        return Objects.equals(text, zipCode.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
