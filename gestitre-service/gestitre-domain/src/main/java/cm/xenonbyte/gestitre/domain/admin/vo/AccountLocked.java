package cm.xenonbyte.gestitre.domain.admin.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public record AccountLocked(Boolean value) {
    public AccountLocked(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static AccountLocked with(Boolean value) {
        Assert.field("Account Locked", value)
                .notNull();
        return new AccountLocked(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountLocked that = (AccountLocked) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
