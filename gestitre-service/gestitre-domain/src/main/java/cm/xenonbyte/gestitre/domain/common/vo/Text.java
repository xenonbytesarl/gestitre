package cm.xenonbyte.gestitre.domain.common.vo;

import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 20/08/2024
 */
public record Text(String value) {

    public Text(@Nonnull String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static Text of(@Nonnull String value) {
        return new Text(value);
    }

    public Boolean isEmpty() {
        return value.isEmpty();
    }


    public Text concat(@Nonnull String value) {
        return new Text(this.value.concat(value));
    }

    public Text replace(@Nonnull String oldValue, @Nonnull String newValue) {
        return Text.of(this.value.replace(oldValue, newValue));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Text text = (Text) object;
        return Objects.equals(value, text.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
