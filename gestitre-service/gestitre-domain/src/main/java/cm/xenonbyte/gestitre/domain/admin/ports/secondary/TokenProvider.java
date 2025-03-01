package cm.xenonbyte.gestitre.domain.admin.ports.secondary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface TokenProvider {
    @Nonnull Token generateToken(@Nonnull User user);

    Boolean isValid(@Nonnull Text refreshToken);

    @Nonnull Email getEmail(@Nonnull Text refreshToken);

    @Nonnull Text refreshAccessToken(@Nonnull User user);
}
