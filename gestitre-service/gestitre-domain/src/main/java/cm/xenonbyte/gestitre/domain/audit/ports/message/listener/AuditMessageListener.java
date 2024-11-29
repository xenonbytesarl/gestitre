package cm.xenonbyte.gestitre.domain.audit.ports.message.listener;

import cm.xenonbyte.gestitre.domain.admin.UserUpdatedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationVerifiedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
public interface AuditMessageListener {

    void handle(CompanyCreatedEvent event);
    void handle(CompanyUpdatedEvent event);
    void handle(UserCreatedEvent event);
    void handle(UserUpdatedEvent event);
    void handle(TenantCreatedEvent event);
    void handle(VerificationCreatedEvent event);
    void handle(VerificationCanceledEvent event);
    void handle(VerificationVerifiedEvent event);
}
