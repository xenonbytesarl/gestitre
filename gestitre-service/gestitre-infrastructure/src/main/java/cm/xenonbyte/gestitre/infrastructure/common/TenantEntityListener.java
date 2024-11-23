package cm.xenonbyte.gestitre.infrastructure.common;

import cm.xenonbyte.gestitre.domain.tenant.TenantContext;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public final class TenantEntityListener {

    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object object) {
        if(object instanceof Tenantable) {
            //TODO remove the if block when authentication and authorization is complete
            if(TenantContext.current() == null) {
                TenantContext.set(UUID.fromString("01935926-a157-71a9-a0d6-63b14be4c4b9"));
            }
            ((Tenantable) object).setTenantId(TenantContext.current());
        }
    }

    @PreRemove
    public void preRemove(Object object) {
        if(object instanceof Tenantable && !((Tenantable) object).getTenantId().equals(TenantContext.current())) {
            throw new EntityNotFoundException();
        }
    }
}
