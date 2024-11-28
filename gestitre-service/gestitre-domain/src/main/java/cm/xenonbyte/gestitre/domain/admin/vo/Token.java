package cm.xenonbyte.gestitre.domain.admin.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public record Token(Text accessToken, Text refreshToken) {
    public Token(@Nonnull Text accessToken, @Nonnull Text refreshToken) {
        this.accessToken = Objects.requireNonNull(accessToken);
        this.refreshToken = Objects.requireNonNull(refreshToken);
    }

    @Nonnull
    public static Token of(Text accessToken, Text refreshToken) {
        Assert.field("Access Token", accessToken)
                .notNull()
                .notNull(accessToken.value())
                .notEmpty(accessToken.value());

        Assert.field("Refresh Token", refreshToken)
                .notNull()
                .notNull(refreshToken.value())
                .notEmpty(refreshToken.value());
        return new Token(accessToken, refreshToken);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(accessToken, token.accessToken) && Objects.equals(refreshToken, token.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken);
    }
}
