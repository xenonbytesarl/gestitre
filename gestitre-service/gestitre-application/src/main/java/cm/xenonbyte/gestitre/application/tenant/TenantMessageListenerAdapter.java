package cm.xenonbyte.gestitre.application.tenant;

import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.Tenant;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantMessageListener;
import cm.xenonbyte.gestitre.domain.tenant.ports.primary.message.listener.TenantService;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.COMPANY_CREATED;


/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class TenantMessageListenerAdapter implements TenantMessageListener {

    private final TenantService tenantService;

    public TenantMessageListenerAdapter(@Nonnull TenantService tenantService) {
        this.tenantService = Objects.requireNonNull(tenantService);
    }

    @Override
    @Blocking
    @ConsumeEvent(value = COMPANY_CREATED)
    public void handle(CompanyCreatedEvent event) {
        log.info("Receiving event from CompanyMessagePublisher to create tenant with name {}", event.getCompany().getCompanyName().text().value());
        tenantService.create(Tenant.builder().name(Name.of(event.getCompany().getCompanyName().text())).build());

    }
}
