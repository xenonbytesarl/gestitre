package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 26/10/2024
 */
public record Note(Text text) {

    public Note(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Note of(Text note) {
        Assert.field("Note", note)
                .notNull()
                .notNull(note.value())
                .notEmpty(note.value());
        return new Note(note);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Note note = (Note) object;
        return Objects.equals(text, note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
