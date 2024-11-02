package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record PageInfoField(Text text) {

    public PageInfoField(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static PageInfoField of(Text field) {
        Assert.field("Field", field)
                .notNull()
                .notNull(field.value())
                .notEmpty(field.value());
        return new PageInfoField(field);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PageInfoField pageInfoField = (PageInfoField) object;
        return Objects.equals(text, pageInfoField.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
