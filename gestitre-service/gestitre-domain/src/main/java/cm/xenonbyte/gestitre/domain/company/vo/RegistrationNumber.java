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
public record RegistrationNumber(Text text) {
    public RegistrationNumber(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static RegistrationNumber of(Text registrationNumber) {
        Assert.field("RegistrationNumber", registrationNumber)
                .notNull()
                .notNull(registrationNumber.value())
                .notEmpty(registrationNumber.value());
        return new RegistrationNumber(registrationNumber);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        RegistrationNumber that = (RegistrationNumber) object;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
