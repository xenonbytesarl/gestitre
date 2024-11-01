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
public record WebSiteUrl(Text text) {
    public WebSiteUrl(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static WebSiteUrl of(Text webSiteUrl) {
        Assert.field("Web Site Url", webSiteUrl)
                .notNull()
                .notNull(webSiteUrl.value())
                .notEmpty(webSiteUrl.value());
        return new WebSiteUrl(webSiteUrl);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WebSiteUrl that = (WebSiteUrl) object;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
