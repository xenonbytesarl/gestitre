package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 21/08/2024
 */
public record Reference(Text text) {


    public Reference(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    public static Reference of(@Nonnull Text reference) {
        Assert.field("Reference", reference)
                .notNull()
                .notNull(reference.value())
                .notEmpty(reference.value());
        return new Reference(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return Objects.equals(text, reference.text);
    }

}
