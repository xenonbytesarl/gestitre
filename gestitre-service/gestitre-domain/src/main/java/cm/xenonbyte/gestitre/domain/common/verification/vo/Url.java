package cm.xenonbyte.gestitre.domain.common.verification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public record Url(Text text) {
    public Url(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Url of(Text url) {
        Assert.field("Url", url)
                .notNull()
                .notNull(url.value())
                .notEmpty(url.value());
        return new Url(url);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Url url = (Url) object;
        return Objects.equals(text, url.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
