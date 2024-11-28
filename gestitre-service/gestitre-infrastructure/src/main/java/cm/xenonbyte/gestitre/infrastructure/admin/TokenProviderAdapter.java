package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Slf4j
@ApplicationScoped
public final class TokenProviderAdapter implements TokenProvider {


    private final String issuer;
    private final Long accessTokenDuration;
    private final Long refreshTokenDuration;

    public TokenProviderAdapter(
            @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer,
            @ConfigProperty(name = "gestitre.jwt.access.token.duration") Long accessTokenDuration,
            @ConfigProperty(name = "gestitre.jwt.refresh.token.duration") Long refreshTokenDuration
    ) {
        this.issuer = Objects.requireNonNull(issuer);
        this.accessTokenDuration = Objects.requireNonNull(accessTokenDuration);
        this.refreshTokenDuration = Objects.requireNonNull(refreshTokenDuration);
    }


    @Nonnull
    @Override
    public Token generateToken(@Nonnull User user) {
        return Token.of(generateAccessToken(user), refreshAccessToken(user));
    }

    private Text refreshAccessToken(User user) {
        return Text.of(
                Jwt
                    .issuer(issuer)
                    .subject(user.getId().getValue().toString())
                    .claim("tenantId", user.getTenantId().getValue().toString())
                    .claim(Claims.email, user.getEmail().text().value())
                    .issuedAt(Instant.now())
                    .expiresIn(refreshTokenDuration)
                    .sign()

        );
    }

    private Text generateAccessToken(User user) {
        Set<String> authorities = user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> permission.getName().text().value())
                .collect(Collectors.toSet());
        return Text.of(
                Jwt
                    .issuer(issuer)
                    .subject(user.getId().getValue().toString())
                    .issuedAt(Instant.now())
                    .expiresIn(accessTokenDuration)
                    .claim(Claims.full_name, user.getName().text().value())
                    .claim(Claims.email, user.getEmail().text().value())
                    .claim("tenantId", user.getTenantId().getValue().toString())
                    .claim("use_mfa", user.getUseMfa().value())
                    .groups(authorities)
                    .sign()

        );
    }


}
