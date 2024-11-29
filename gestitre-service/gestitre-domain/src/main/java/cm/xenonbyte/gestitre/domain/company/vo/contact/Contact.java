package cm.xenonbyte.gestitre.domain.company.vo.contact;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Contact(Phone phone, Fax fax, Email email, Name name) {

    public Contact(Phone phone, Fax fax, Email email, Name name) {
        this.phone = phone;
        this.fax = fax;
        this.email = Objects.requireNonNull(email);
        this.name = Objects.requireNonNull(name);
    }

    private Contact(Builder builder) {
        this(builder.phone, builder.fax, builder.email, builder.name);
    }

    public static Contact of(Phone phone, Fax fax, Email email, Name name) {
        Assert.field("Phone", phone)
                .notNull()
                .notNull(phone.text())
                .notNull(phone.text().value());

        Assert.field("Fax", fax)
                .notNull()
                .notNull(fax.text())
                .notNull(fax.text().value());

        Assert.field("Email", email)
                .notNull()
                .notNull(email.text())
                .notNull(email.text().value());

        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value());

        return new Contact(phone, fax, email, name);
    }

    public static Contact of(Email email, Name name) {


        Assert.field("Email", email)
                .notNull()
                .notNull(email.text())
                .notNull(email.text().value());

        Assert.field("Name", name)
                .notNull()
                .notNull(name.text())
                .notNull(name.text().value())
                .notEmpty(name.text().value());

        return new Contact(null, null, email, name);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private Phone phone;
        private Fax fax;
        private Email email;
        private Name name;

        private Builder() {
        }

        public Builder phone(Phone val) {
            phone = val;
            return this;
        }

        public Builder fax(Fax val) {
            fax = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Contact contact = (Contact) object;
        return Objects.equals(fax, contact.fax) && Objects.equals(name, contact.name) && Objects.equals(phone, contact.phone) && Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, fax, email, name);
    }
}
