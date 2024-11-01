package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/08/2024
 */
public record Filename(Text text) {

    public Filename(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Filename of(Text filename) {
        Assert.field("Filename", filename)
                .notNull()
                .notNull(filename.value())
                .notEmpty(filename.value());
        return new Filename(filename);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Filename filename = (Filename) object;
        return Objects.equals(text, filename.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
