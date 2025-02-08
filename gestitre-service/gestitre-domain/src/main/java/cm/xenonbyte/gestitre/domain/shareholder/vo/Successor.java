package cm.xenonbyte.gestitre.domain.shareholder.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public record Successor(Name name, Email email, Phone phone) {
    public Successor(@Nonnull Name name, Email email, @Nonnull Phone phone) {
        this.name = Objects.requireNonNull(name);
        this.email = email;
        this.phone = Objects.requireNonNull(phone);
    }

    @Nonnull
    public static Successor of(Name name, Phone phone) {
        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value())
                .notEmpty(name.text().value());
        Assert.field("Phone", phone)
                .notNull()
                .notNull(phone.text())
                .notNull(phone.text().value())
                .notEmpty(phone.text().value());
        return new Successor(name, null, phone);
    }

    @Nonnull
    public static Successor of(Name name, Email email, Phone phone) {
        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value())
                .notEmpty(name.text().value());
        Assert.field("Phone", phone)
                .notNull()
                .notNull(phone.text())
                .notNull(phone.text().value())
                .notEmpty(phone.text().value());
        return new Successor(name, email, phone);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Successor successor = (Successor) o;
        return Objects.equals(name, successor.name) && Objects.equals(email, successor.email) && Objects.equals(phone, successor.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone);
    }
}
