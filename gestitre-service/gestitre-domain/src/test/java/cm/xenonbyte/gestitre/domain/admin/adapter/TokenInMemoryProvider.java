package cm.xenonbyte.gestitre.domain.admin.adapter;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public final class TokenInMemoryProvider implements TokenProvider {
    @Nonnull
    @Override
    public Token generateToken(@Nonnull User user) {
        return new Token(Text.of("KOCNOCEO657E8ECC6E7CEC98EC8E9"), Text.of("HBCECEOCEUCECIUE"));
    }

    @Override
    public Boolean isValid(@Nonnull Text refreshToken) {
        return null;
    }

    @Nonnull
    @Override
    public Email getEmail(@Nonnull Text refreshToken) {
        return null;
    }

    @Nonnull
    @Override
    public Text refreshAccessToken(@Nonnull User user) {
        return null;
    }
}