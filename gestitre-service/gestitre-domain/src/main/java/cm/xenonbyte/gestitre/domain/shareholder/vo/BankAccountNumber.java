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
public record BankAccountNumber(Text text) {
    public BankAccountNumber(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static BankAccountNumber of(Text bankAccountNumber) {
        Assert.field("Account Number", bankAccountNumber)
                .notNull()
                .notNull(bankAccountNumber.value())
                .notEmpty(bankAccountNumber.value());
        return new BankAccountNumber(bankAccountNumber);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BankAccountNumber bankAccountNumber = (BankAccountNumber) object;
        return Objects.equals(text, bankAccountNumber.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}