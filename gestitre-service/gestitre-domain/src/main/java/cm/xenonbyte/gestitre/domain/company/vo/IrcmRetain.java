package cm.xenonbyte.gestitre.domain.company.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Money;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public record IrcmRetain(Money amount) {
    public IrcmRetain(@Nonnull Money amount) {
        this.amount = Objects.requireNonNull(amount);
    }

    @Nonnull
    public static IrcmRetain of(Money ircmRetain) {
        Assert.field("IRCM retain", ircmRetain)
                .notNull()
                .notNull(ircmRetain.amount())
                .notNull(ircmRetain.amount())
                .notPositive(ircmRetain.amount());
        return new IrcmRetain(ircmRetain);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        IrcmRetain that = (IrcmRetain) object;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
