package cm.xenonbyte.gestitre.infrastructure.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.UUID;

import static cm.xenonbyte.gestitre.infrastructure.common.Tenantable.TENANT_COLUMN_NAME;
import static cm.xenonbyte.gestitre.infrastructure.common.Tenantable.TENANT_FILTER_NAME;
import static cm.xenonbyte.gestitre.infrastructure.common.Tenantable.TENANT_PARAMETER_NAME;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Setter
@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(TenantEntityListener.class)
@Filter(name = TENANT_FILTER_NAME)
@FilterDef(
        name = TENANT_FILTER_NAME,
        parameters = @ParamDef(name = TENANT_PARAMETER_NAME, type = String.class),
        defaultCondition = TENANT_COLUMN_NAME + " = :" + TENANT_PARAMETER_NAME
)
public class Tenantable extends Auditable {

    public static final String TENANT_FILTER_NAME = "tenantFilter";
    public static final String TENANT_PARAMETER_NAME = "tenantId";
    public static final String TENANT_COLUMN_NAME = "c_tenant_id";

    @Column(name = "c_tenant_id", length = 128)
    private UUID tenantId;
}
