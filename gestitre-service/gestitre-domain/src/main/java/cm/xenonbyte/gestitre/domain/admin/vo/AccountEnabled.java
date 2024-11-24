package cm.xenonbyte.gestitre.domain.admin.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public record AccountEnabled(Boolean value) {

    public AccountEnabled(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static AccountEnabled with(Boolean value) {
        Assert.field("AccountEnabled", value)
                .notNull();
        return new AccountEnabled(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEnabled that = (AccountEnabled) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
