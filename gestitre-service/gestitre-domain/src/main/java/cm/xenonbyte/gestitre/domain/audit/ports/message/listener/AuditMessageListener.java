package cm.xenonbyte.gestitre.domain.audit.ports.message.listener;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserPasswordResetedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserUpdatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationVerifiedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailFailedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailSentEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerConfirmedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailServerUpdatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.notification.event.MailTemplateUpdatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderUpdatedEvent;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveCreatedEvent;
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
    void handle(UserPasswordResetedEvent event);
    void handle(TenantCreatedEvent event);
    void handle(VerificationCreatedEvent event);
    void handle(VerificationCanceledEvent event);
    void handle(VerificationVerifiedEvent event);
    void handle(ShareHolderCreatedEvent event);
    void handle(ShareHolderUpdatedEvent event);
    void handle(StockMoveCreatedEvent event);
    void handle(MailServerCreatedEvent event);
    void handle(MailServerUpdatedEvent event);
    void handle(MailServerConfirmedEvent event);
    void handle(MailTemplateCreatedEvent event);
    void handle(MailTemplateUpdatedEvent event);
    void handle(MailCreatedEvent event);
    void handle(MailFailedEvent event);
    void handle(MailSentEvent event);
}
