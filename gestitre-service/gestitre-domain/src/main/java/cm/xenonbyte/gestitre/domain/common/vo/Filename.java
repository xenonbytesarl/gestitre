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
    @Nonnull
    public Text text() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filename name = (Filename) o;
        return Objects.equals(text, name.text);
    }

}
