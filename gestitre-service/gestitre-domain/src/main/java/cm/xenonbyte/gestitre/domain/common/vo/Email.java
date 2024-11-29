package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Email(Text text) {
    public Email(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Email of(Text email) {
        Assert.field("Email", email)
                .notNull()
                .notNull(email.value())
                .notEmpty(email.value())
                .isEmail(email.value());
        return new Email(email);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Email email = (Email) object;
        return Objects.equals(text, email.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
