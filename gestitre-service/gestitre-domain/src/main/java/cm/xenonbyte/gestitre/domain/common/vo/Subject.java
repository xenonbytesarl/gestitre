package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record Subject(Text text) {

    public Subject(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Subject of(Text subject) {
        Assert.field("Subject", subject)
                .notNull()
                .notNull(subject.value())
                .notEmpty(subject.value());
        return new Subject(subject);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Subject subject = (Subject) object;
        return Objects.equals(text, subject.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
