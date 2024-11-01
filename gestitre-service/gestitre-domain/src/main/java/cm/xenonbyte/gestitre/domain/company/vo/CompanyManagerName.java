package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record CompanyManagerName(Text text) {

    public CompanyManagerName(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static CompanyManagerName of(Text companyManagerName) {
        Assert.field("Company manager name", companyManagerName)
                .notNull()
                .notNull(companyManagerName.value())
                .notEmpty(companyManagerName.value());
        return new CompanyManagerName(companyManagerName);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CompanyManagerName that = (CompanyManagerName) object;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
