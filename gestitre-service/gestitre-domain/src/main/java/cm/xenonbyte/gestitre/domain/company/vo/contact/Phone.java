package cm.xenonbyte.gestitre.domain.company.vo.contact;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Phone(Text text) {
    public Phone(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Phone of(Text phone) {
        Assert.field("Phone", phone)
                .notNull()
                .notNull(phone.value())
                .notEmpty(phone.value())
                .notNumberValue(phone.value());
        return new Phone(phone);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Phone phone = (Phone) object;
        return Objects.equals(text, phone.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
