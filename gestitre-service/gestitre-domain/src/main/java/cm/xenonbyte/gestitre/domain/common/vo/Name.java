package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record Name(Text text) {

    public Name(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Name of(Text name) {
        Assert.field("Name", name)
                .notNull()
                .notNull(name.value())
                .notEmpty(name.value());
        return new Name(name);
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
        Name name = (Name) o;
        return Objects.equals(text, name.text);
    }

}
