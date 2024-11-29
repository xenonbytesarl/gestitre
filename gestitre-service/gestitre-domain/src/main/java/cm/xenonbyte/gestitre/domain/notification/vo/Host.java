package cm.xenonbyte.gestitre.domain.notification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
public record Host(Text text) {
    public Host(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Host of(Text host) {
        Assert.field("Host", host)
                .notNull()
                .notNull(host.value())
                .notEmpty(host.value());
        return new Host(host);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Host host = (Host) object;
        return Objects.equals(text, host.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}