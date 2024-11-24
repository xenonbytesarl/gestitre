package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.domain.company.ports.primary.message.listener.CompanyMessageListener;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.TENANT_CREATED;

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
        log.info(">>>> Receiving event from TenantMessagePublisher to update company with name {}",
                event.getTenant().getName().text().value());
    }


}
