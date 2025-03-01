package cm.xenonbyte.gestitre.application.common.filter;

import cm.xenonbyte.gestitre.application.common.dto.ErrorApiResponse;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationResolver;
import cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.context.TenantContext;
import cm.xenonbyte.gestitre.domain.context.TimezoneContext;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Provider
@Priority(Priorities.AUTHENTICATION)
public final class JwtFilter implements ContainerRequestFilter {

    private static final String TENANT_CODE_HEADER = "X-Gestitre-Tenant-Code";
    private static final String TIMEZONE_HEADER = "X-Gestitre-Timezone";
    private static final String JWT_FILTER_AUTHORIZATION_MISSING = "JwtFilter.1";
    private static final String JWT_FILTER_INVALID_TOKEN = "JwtFilter.2";
    private static final String JWT_FILTER_AUTHORIZATION_INVALID= "JwtFilter.3";

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
            String tenantCode = context.getHeaderString(TENANT_CODE_HEADER);
            if(tenantCode != null) {
                Tenant tenant = tenantService.findByCode(Code.of(Text.of(tenantCode)));
                if(tenant != null) {
                    setTimezoneContext(context);
                    TenantContext.set(tenant.getId().getValue());
                    return;
                }
                abortWithUnAuthorized(context, JWT_FILTER_AUTHORIZATION_INVALID);
                return;
            }
            abortWithUnAuthorized(context, JWT_FILTER_AUTHORIZATION_MISSING);
            return;
        }

        String token = bearerToken.substring(7);
        try{
            jwtParser.parse(token);
            Optional<Object> optionalTenantId = jsonWebToken.claim("tenantId");
            optionalTenantId.ifPresent(o -> TenantContext.set(UUID.fromString(o.toString())));
            setTimezoneContext(context);
            if(TimezoneContext.current() == null) {
                Optional<Object> optionalTimezone = jsonWebToken.claim("timezone");
                optionalTimezone.ifPresent(o -> TimezoneContext.set(o.toString()));
            }
        } catch (ParseException e) {
            abortWithUnAuthorized(context, JWT_FILTER_INVALID_TOKEN);
        }
    }

    private void setTimezoneContext(ContainerRequestContext context) {
        String timezoneHeader = context.getHeaderString(TIMEZONE_HEADER);
        if (timezoneHeader == null) {
            return;
        }
        TimezoneContext.set(timezoneHeader);
    }

    private void abortWithUnAuthorized(ContainerRequestContext context, String message) {
        ErrorApiResponse errorApiResponse = ErrorApiResponse.builder()
                .timestamp(ZonedDateTime.now())
                .code(UNAUTHORIZED.getStatusCode())
                .status(UNAUTHORIZED.name())
                .success(false)
                .reason(LocalizationUtil.getMessage(message, localeResolver.getLocaleFromRequest(), ""))
                .build();
        context.abortWith(
                Response.status(UNAUTHORIZED)
                        .entity(
                                errorApiResponse
                        )
                        .build()
        );
        log.error("{}", errorApiResponse.getReason());
    }
}
