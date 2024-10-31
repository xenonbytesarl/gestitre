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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return Objects.equals(value, text.value);
    }


    public Text concat(@Nonnull String value) {
        return new Text(this.value.concat(value));
    }

    public Text replace(@Nonnull String oldValue, @Nonnull String newValue) {
        return Text.of(this.value.replace(oldValue, newValue));
    }
}
