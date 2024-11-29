package cm.xenonbyte.gestitre.domain.admin.verification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public record Code(Text text) {
    public Code(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Code of(Text code) {
        Assert.field("Code", code)
                .notNull()
                .notNull(code.value())
                .notEmpty(code.value());
        return new Code(code);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Code code = (Code) object;
        return Objects.equals(text, code.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
