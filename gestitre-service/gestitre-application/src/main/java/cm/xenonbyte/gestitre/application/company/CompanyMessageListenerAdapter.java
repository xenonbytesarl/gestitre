package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.ports.primary.message.listener.CompanyMessageListener;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreateEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.TENANT_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Slf4j
@ApplicationScoped
public final class CompanyMessageListenerAdapter implements CompanyMessageListener {

    private final CompanyService companyService;

    public CompanyMessageListenerAdapter(@Nonnull CompanyService companyService) {
        this.companyService = Objects.requireNonNull(companyService);
    }

    @Override
    @Blocking
    @ConsumeEvent(value = TENANT_CREATED)
    public void handle(TenantCreateEvent event) {
        log.info("Receiving event from TenantMessagePublisher to update company with name {}",
                event.getTenant().getName().text().value());
    }
}
