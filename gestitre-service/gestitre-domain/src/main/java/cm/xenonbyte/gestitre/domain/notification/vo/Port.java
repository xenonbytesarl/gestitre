package cm.xenonbyte.gestitre.domain.notification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
* @author bamk
* @version 1.0
* @since 29/11/2024
*/public record Port(Long value) {
public Port(@Nonnull Long value) {
    this.value = Objects.requireNonNull(value);
}

@Nonnull
public static Port of(Long value) {
    Assert.field("Port", value)
            .notNull()
            .notNull(value)
            .notPositive(value);
    return new Port(value);
}

@Override
public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Port port = (Port) object;
    return Objects.equals(value, port.value);
}

@Override
public int hashCode() {
    return Objects.hashCode(value);
}
}