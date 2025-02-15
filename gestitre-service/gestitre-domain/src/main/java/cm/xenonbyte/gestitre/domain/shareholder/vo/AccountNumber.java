package cm.xenonbyte.gestitre.domain.shareholder.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public record AccountNumber(Text text) {
    public AccountNumber(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static AccountNumber of(Text accountNumber) {
        Assert.field("Account Number", accountNumber)
                .notNull()
                .notNull(accountNumber.value())
                .notEmpty(accountNumber.value());
        return new AccountNumber(accountNumber);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AccountNumber accountNumber = (AccountNumber) object;
        return Objects.equals(text, accountNumber.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
