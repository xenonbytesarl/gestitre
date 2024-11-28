package cm.xenonbyte.gestitre.application.common.filter;

import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.TenantContext;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Slf4j
@Provider
public final class TenantFilter implements ContainerRequestFilter {

    private static final String TENANT_ID_HEADER = "X-Gestitre-Tenant-Id";
    private static final String TENANT_CODE_HEADER = "X-Gestitre-Tenant-Code";


    private final TenantService tenantService;

    public TenantFilter(TenantService tenantService) {
        this.tenantService = Objects.requireNonNull(tenantService);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String tenantId = requestContext.getHeaderString(TENANT_ID_HEADER);
        if(tenantId != null) {
            TenantContext.set(UUID.fromString(tenantId));
        } else {
            String tenantName = requestContext.getHeaderString(TENANT_CODE_HEADER);
            if(tenantName != null) {
                Tenant tenant = tenantService.findByName(Name.of(Text.of(tenantName)));
                if(tenant != null) {
                    TenantContext.set(tenant.getId().getValue());
                }
            }
        }
    }
}
