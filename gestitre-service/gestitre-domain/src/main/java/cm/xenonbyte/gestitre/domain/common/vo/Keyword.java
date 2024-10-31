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
    public static Keyword of(Text text) {
        Assert.field("Search keyword", text)
                .notNull();
        return new Keyword(text);
    }

    @Override
    @Nonnull
    public Text text() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(text, keyword.text);
    }

}
