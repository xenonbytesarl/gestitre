package cm.xenonbyte.gestitre.application.audit;

import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserPasswordResetedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserUpdatedEvent;
import cm.xenonbyte.gestitre.domain.audit.ports.message.listener.AuditMessageListener;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCanceledEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationVerifiedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderUpdatedEvent;
import cm.xenonbyte.gestitre.domain.stock.event.StockMoveCreatedEvent;
import cm.xenonbyte.gestitre.domain.tenant.TenantCreatedEvent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.COMPANY_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.COMPANY_UPDATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.SHAREHOLDER_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.SHAREHOLDER_UPDATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.STOCK_MOVE_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.TENANT_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.USER_ACTIVATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.USER_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.USER_PASSWORD_RESET;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.VERIFICATION_CANCELED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.VERIFICATION_CREATED;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.VERIFICATION_VERIFIED;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class AuditMessageListenerAdapter implements AuditMessageListener {

    @Override
    @Blocking
    @ConsumeEvent(value = COMPANY_CREATED)
    public void handle(CompanyCreatedEvent event) {
        log.info(">>>> Receiving event from CompanyMessagePublisher to create audit for new company with name {}", event.getCompany().getCompanyName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = COMPANY_UPDATED)
    public void handle(CompanyUpdatedEvent event) {
        log.info(">>>> Receiving event from CompanyMessagePublisher to create audit for updated company with name {}", event.getCompany().getCompanyName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = USER_CREATED)
    public void handle(UserCreatedEvent event) {
        log.info(">>>> Receiving event from UserMessagePublisher to create audit for new user with name {}", event.getUser().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = USER_ACTIVATED)
    public void handle(UserUpdatedEvent event) {
        log.info(">>>> Receiving event from UserMessagePublisher to create audit for activated user with name {}", event.getUser().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = USER_PASSWORD_RESET)
    public void handle(UserPasswordResetedEvent event) {
        log.info(">>>> Receiving event from UserMessagePublisher to create audit <password reset for user with name {}>", event.getUser().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = TENANT_CREATED)
    public void handle(TenantCreatedEvent event) {
        log.info(">>>> Receiving event from TenantMessagePublisher to create audit <new tenant with name {}>", event.getTenant().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = VERIFICATION_CREATED)
    public void handle(VerificationCreatedEvent event) {
        log.info(">>>> Receiving event from VerificationMessagePublisher to create audit <verification with of type {}>", event.getVerification().getType().name());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = VERIFICATION_CANCELED)
    public void handle(VerificationCanceledEvent event) {
        log.info(">>>> Receiving event from VerificationMessagePublisher to create audit  <canceled verification with of type {}>", event.getVerification().getType().name());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = VERIFICATION_VERIFIED)
    public void handle(VerificationVerifiedEvent event) {
        log.info(">>>> Receiving event from VerificationMessagePublisher to create audit <verified verification with of type {}>", event.getVerification().getType().name());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = SHAREHOLDER_CREATED)
    public void handle(ShareHolderCreatedEvent event) {
        log.info(">>>> Receiving event from ShareHolderMessagePublisher to create audit <create share holder with name {}>", event.getShareHolder().getName().text().value());
    }

    @Override
    @Blocking
    @ConsumeEvent(value = SHAREHOLDER_UPDATED)
    public void handle(ShareHolderUpdatedEvent event) {
        log.info(">>>> Receiving event from ShareHolderMessagePublisher to create audit  <update share holder with name {}>", event.getShareHolder().getName().text().value());

    }

    @Override
    @Blocking
    @ConsumeEvent(value = STOCK_MOVE_CREATED)
    public void handle(StockMoveCreatedEvent event) {
        log.info(">>>> Receiving event from StockMoveMessagePublisher to create audit  <create stock move with reference {}>", event.getStockMove().getReference().text().value());

    }
}
