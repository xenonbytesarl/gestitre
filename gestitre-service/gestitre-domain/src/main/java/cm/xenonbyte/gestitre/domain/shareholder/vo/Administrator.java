package cm.xenonbyte.gestitre.domain.shareholder.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public record Administrator(Text text) {

    public Administrator(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Administrator of(Text administrator) {
        Assert.field("Administrator", administrator)
                .notNull()
                .notNull(administrator.value())
                .notEmpty(administrator.value());
        return new Administrator(administrator);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Administrator administrator = (Administrator) object;
        return Objects.equals(text, administrator.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}