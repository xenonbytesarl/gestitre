package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record CompanyName(Text text) {

    public CompanyName(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static CompanyName of(Text companyName) {
        Assert.field("Company name", companyName)
                .notNull()
                .notNull(companyName.value())
                .notEmpty(companyName.value());
        return new CompanyName(companyName);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CompanyName that = (CompanyName) object;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
