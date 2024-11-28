package cm.xenonbyte.gestitre.infrastructure.common.interceptor;

import cm.xenonbyte.gestitre.domain.tenant.TenantContext;
import cm.xenonbyte.gestitre.infrastructure.common.Tenantable;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.DisableTenantFilter;
import cm.xenonbyte.gestitre.infrastructure.common.annotation.TenantPanacheInterceptor;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Slf4j
@Interceptor
@TenantPanacheInterceptor
public final class TenantInterceptor {

    private final EntityManager entityManager;

    public TenantInterceptor(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager);
    }

    @AroundInvoke
    public Object aroundInvoke(InvocationContext context) throws Exception {
        entityManager.unwrap(Session.class)
                .disableFilter(Tenantable.TENANT_FILTER_NAME);

        Method method = context.getMethod();
        if(!method.isAnnotationPresent(DisableTenantFilter.class)) {
            entityManager.unwrap(Session.class)
                .enableFilter(Tenantable.TENANT_FILTER_NAME)
                .setParameter(Tenantable.TENANT_PARAMETER_NAME, TenantContext.current());
        }
        Object result;
        try {
            result = context.proceed(); // Call the original method
        } catch (Exception e) {
            log.error("Error in Panache method: {}", context.getMethod().getName(), e);
            throw e;
        }

        return result;

    }
}
