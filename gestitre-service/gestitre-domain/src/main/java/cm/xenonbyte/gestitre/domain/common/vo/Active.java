package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 06/08/2024
 */
public record Active(Boolean value) {


    public Active(@Nonnull Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public static Active with(Boolean value) {
        Assert.field("Active", value)
                .notNull();
        return new Active(value);
    }

    @Override
    @Nonnull
    public Boolean value() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Active active = (Active) o;
        return Objects.equals(value, active.value);
    }

}
