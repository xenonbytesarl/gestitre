package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public record EndLicence(ZonedDateTime createAt, Licence licence) {

    public EndLicence(@Nonnull ZonedDateTime createAt, @Nonnull Licence licence) {
        this.createAt = requireNonNull(createAt);
        this.licence = requireNonNull(licence);
    }

    @Nonnull
    public static EndLicence of(@Nonnull ZonedDateTime createAt, @Nonnull Licence licence) {
        Assert.field("createAt", createAt)
                .notNull();
        Assert.field("licence", licence)
                .notNull();
        return new EndLicence(createAt, licence);
    }

    public ZonedDateTime computeEndLicenceDate() {
        return switch (licence) {
            case MONTH_12 -> createAt.plusMonths(12);
            case MONTH_24 -> createAt.plusMonths(24);
            case MONTH_36 -> createAt.plusMonths(36);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndLicence endLicence = (EndLicence) o;
        return licence == endLicence.licence && Objects.equals(createAt, endLicence.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createAt, licence);
    }
}
