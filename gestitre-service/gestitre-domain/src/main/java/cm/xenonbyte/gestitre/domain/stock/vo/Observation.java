package cm.xenonbyte.gestitre.domain.stock.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public record Observation(Text text) {
    public Observation(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Observation of(Text observation) {
        Assert.field("Observation", observation)
                .notNull()
                .notNull(observation.value())
                .notEmpty(observation.value());
        return new Observation(observation);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Observation observation = (Observation) object;
        return Objects.equals(text, observation.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
