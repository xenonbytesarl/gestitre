package cm.xenonbyte.gestitre.domain.security.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public record Password(Text text) {
    public Password(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Password of(Text password) {
        Assert.field("Password", password)
                .notNull()
                .notNull(password.value())
                .notEmpty(password.value());
        return new Password(password);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Password password = (Password) object;
        return Objects.equals(text, password.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
