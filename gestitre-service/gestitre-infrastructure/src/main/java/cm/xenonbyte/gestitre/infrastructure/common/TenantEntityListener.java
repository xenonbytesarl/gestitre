package cm.xenonbyte.gestitre.infrastructure.common;

import cm.xenonbyte.gestitre.domain.context.TenantContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
public final class TenantEntityListener {

    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object object) {
        if(object instanceof Tenantable && ((Tenantable) object).getTenantId() == null) {
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
