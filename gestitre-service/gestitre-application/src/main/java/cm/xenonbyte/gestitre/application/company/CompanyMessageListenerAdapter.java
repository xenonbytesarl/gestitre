package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.primary.message.listener.CompanyMessageListener;
import cm.xenonbyte.gestitre.domain.security.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.COMPANY_UPDATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.TENANT_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.USER_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class CompanyMessageListenerAdapter implements CompanyMessageListener {

    @Override
    @Blocking
    @ConsumeEvent(value = TENANT_CREATED)
    public void handle(TenantCreatedEvent event) {
        log.info("Receiving event from TenantMessagePublisher to update company with name {}",
                event.getTenant().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = USER_CREATED)
    public void handle(UserCreatedEvent event) {
        log.info("Receiving event from TenantMessagePublisher to update company with name {}",
                event.getUser().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = COMPANY_UPDATED)
    public void handle(CompanyUpdatedEvent event) {
        log.info("Receiving event from TenantMessagePublisher to update company with name {}",
                event.getCompany().getCompanyName().text().value());
    }
}
