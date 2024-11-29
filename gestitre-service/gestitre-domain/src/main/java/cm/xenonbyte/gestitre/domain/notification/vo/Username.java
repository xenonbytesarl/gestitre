package cm.xenonbyte.gestitre.domain.notification.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
* @author bamk
* @version 1.0
* @since 29/11/2024
*/public record Username(Text text) {
    public Username(@Nonnull Text text) {
        this.text = Objects.requireNonNull(text);
    }

    @Nonnull
    public static Username of(Text username) {
        Assert.field("Username", username)
                .notNull()
                .notNull(username.value())
                .notEmpty(username.value());
        return new Username(username);
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Username username = (Username) object;
        return Objects.equals(text, username.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}