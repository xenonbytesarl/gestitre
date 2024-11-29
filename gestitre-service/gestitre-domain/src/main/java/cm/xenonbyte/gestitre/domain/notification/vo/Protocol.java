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
public record Protocol(Text text) {
    public Protocol(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Protocol of(Text message) {
        Assert.field("Protocol", message)
                .notNull()
                .notNull(message.value())
                .notEmpty(message.value());
        return new Protocol(message);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Protocol message = (Protocol) object;
        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
