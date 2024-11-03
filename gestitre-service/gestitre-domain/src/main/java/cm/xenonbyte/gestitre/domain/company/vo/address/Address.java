package cm.xenonbyte.gestitre.domain.company.vo.address;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Address(Street street, City city, ZipCode zipCode, Country country) {
    public Address(Street street, City city, ZipCode zipCode, Country country) {
        this.street = street;
        this.city = Objects.requireNonNull(city);
        this.zipCode = Objects.requireNonNull(zipCode);
        this.country = Objects.requireNonNull(country);
    }

    private Address(Builder builder) {
        this(builder.street, builder.city, builder.zipCode, builder.country);
    }


    public static Address of(ZipCode zipCode, City city, Country country) {
        Assert.field("Zip code", zipCode)
                .notNull()
                .notNull(zipCode.text())
                .notNull(zipCode.text().value())
                .notEmpty(zipCode.text().value())
                .notNumberValue(zipCode.text().value());

        Assert.field("City", city)
                .notNull()
                .notNull(city.text())
                .notNull(city.text().value())
                .notEmpty(city.text().value());

        Assert.field("Country", country)
                .notNull()
                .notNull(country.text())
                .notNull(country.text().value())
                .notEmpty(country.text().value());

        return new Address(null, city, zipCode, country);
    }

    public static Address of(Street street, ZipCode zipCode, City city, Country country) {
        Assert.field("Street", street)
                .notNull()
                .notNull(street.text())
                .notNull(street.text().value())
                .notEmpty(street.text().value());

        Assert.field("Zip code", zipCode)
                .notNull()
                .notNull(zipCode.text())
                .notNull(zipCode.text().value())
                .notEmpty(zipCode.text().value())
                .notNumberValue(zipCode.text().value());

        Assert.field("City", city)
                .notNull()
                .notNull(city.text())
                .notNull(city.text().value())
                .notEmpty(city.text().value());

        Assert.field("Country", country)
                .notNull()
                .notNull(country.text())
                .notNull(country.text().value())
                .notEmpty(country.text().value());

        return new Address(street, city, zipCode, country);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private Street street;
        private City city;
        private ZipCode zipCode;
        private Country country;

        private Builder() {
        }

        public Builder street(Street val) {
            street = val;
            return this;
        }

        public Builder city(City val) {
            city = val;
            return this;
        }

        public Builder zipCode(ZipCode val) {
            zipCode = val;
            return this;
        }

        public Builder country(Country val) {
            country = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
