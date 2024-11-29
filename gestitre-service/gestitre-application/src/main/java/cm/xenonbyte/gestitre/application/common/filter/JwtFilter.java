package cm.xenonbyte.gestitre.application.common.filter;

import cm.xenonbyte.gestitre.application.common.dto.ErrorApiResponse;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationResolver;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantContext;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public final class JwtFilter implements ContainerRequestFilter {

    private static final String TENANT_CODE_HEADER = "X-Gestitre-Tenant-Code";
    private static final String JWT_FILTER_AUTHORIZATION_MISSING = "JwtFilter.1";
    private static final String JWT_FILTER_INVALID_TOKEN = "JwtFilter.2";

    private final TenantService tenantService;
    private final LocalizationResolver localeResolver;
    private final JWTParser jwtParser;
    private final JsonWebToken jsonWebToken;

    public JwtFilter(TenantService tenantService, LocalizationResolver localeResolver, JWTParser jwtParser, JsonWebToken jsonWebToken) {
        this.tenantService = Objects.requireNonNull(tenantService);
        this.localeResolver = Objects.requireNonNull(localeResolver);
        this.jwtParser = Objects.requireNonNull(jwtParser);
        this.jsonWebToken = Objects.requireNonNull(jsonWebToken);
    }

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        String bearerToken = context.getHeaderString(AUTHORIZATION);

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            String tenantName = context.getHeaderString(TENANT_CODE_HEADER);
            if(tenantName != null) {
                Tenant tenant = tenantService.findByName(Name.of(Text.of(tenantName)));
                if(tenant != null) {
                    TenantContext.set(tenant.getId().getValue());
                    return;
                }
            } else {
                abortWithUnAuthorized(context, JWT_FILTER_AUTHORIZATION_MISSING);
                return;
            }

        }

        String token = bearerToken.substring(7);
        try{
            jwtParser.parse(token);
            Optional<Object> optionalTenantId = jsonWebToken.claim("tenantId");
            optionalTenantId.ifPresent(o -> TenantContext.set(UUID.fromString(o.toString())));
        } catch (ParseException e) {
            abortWithUnAuthorized(context, JWT_FILTER_INVALID_TOKEN);
        }
    }

    private void abortWithUnAuthorized(ContainerRequestContext context, String message) {
        context.abortWith(
                Response.status(UNAUTHORIZED)
                        .entity(
                            ErrorApiResponse.builder()
                                .timestamp(ZonedDateTime.now())
                                .code(UNAUTHORIZED.getStatusCode())
                                .status(UNAUTHORIZED.name())
                                .success(false)
                                .reason(LocalizationUtil.getMessage(message, localeResolver.getLocaleFromRequest(), ""))
                                .build()
                        )
                        .build()
        );
    }
}
