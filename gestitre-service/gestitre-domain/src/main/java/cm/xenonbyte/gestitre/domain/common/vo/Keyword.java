package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 30/08/2024
 */
public record Keyword(Text text) {
    public Keyword(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Keyword of(Text keyword) {
        Assert.field("Search keyword", keyword)
                .notNull()
                .notNull(keyword.value());
        return new Keyword(keyword);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Keyword keyword = (Keyword) object;
        return Objects.equals(text, keyword.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
