package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record IsinCode(Text text) {
    public IsinCode(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static IsinCode of(Text isinCode) {
        Assert.field("Isin Code", isinCode)
                .notNull()
                .notNull(isinCode.value())
                .notEmpty(isinCode.value());
        return new IsinCode(isinCode);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        IsinCode isinCode = (IsinCode) object;
        return Objects.equals(text, isinCode.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
