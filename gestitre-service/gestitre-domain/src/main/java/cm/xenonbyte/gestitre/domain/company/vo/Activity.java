package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record Activity(Text text) {
    public Activity(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Activity of(Text activity) {
        Assert.field("Activity", activity)
                .notNull()
                .notNull(activity.value())
                .notEmpty(activity.value());
        return new Activity(activity);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Activity activity = (Activity) object;
        return Objects.equals(text, activity.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}
