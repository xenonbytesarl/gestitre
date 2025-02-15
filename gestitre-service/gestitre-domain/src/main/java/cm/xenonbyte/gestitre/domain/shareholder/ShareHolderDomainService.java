package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderCreatedEvent;
import cm.xenonbyte.gestitre.domain.shareholder.ports.primary.ShareHolderService;
import cm.xenonbyte.gestitre.domain.shareholder.ports.secondary.ShareHolderRepository;
import cm.xenonbyte.gestitre.domain.shareholder.ports.secondary.message.ShareHolderMessagePublisher;
import cm.xenonbyte.gestitre.domain.shareholder.vo.AccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.validatePageParameters;
import static cm.xenonbyte.gestitre.domain.shareholder.event.ShareHolderEventType.SHAREHOLDER_CREATED;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@DomainService
public final class ShareHolderDomainService implements ShareHolderService {

    private static final Logger LOGGER = Logger.getLogger(ShareHolderDomainService.class.getName());

    private final ShareHolderRepository shareHolderRepository;
    private final ShareHolderMessagePublisher shareHolderMessagePublisher;

    public ShareHolderDomainService(
            @Nonnull ShareHolderRepository shareHolderRepository,
            @Nonnull ShareHolderMessagePublisher shareHolderMessagePublisher) {
        this.shareHolderRepository = Objects.requireNonNull(shareHolderRepository);
        this.shareHolderMessagePublisher = Objects.requireNonNull(shareHolderMessagePublisher);
    }


    @Override
    public ShareHolderCreatedEvent createShareHolder(ShareHolder shareHolder) {
        shareHolder.validateMandatoryFields();
        validateShareHolder(shareHolder);
        shareHolder.initializeDefaultValues();
        shareHolder = shareHolderRepository.create(shareHolder);
        LOGGER.info("Shareholder is created with id " + shareHolder.getId().getValue());
        ShareHolderCreatedEvent shareHolderCreatedEvent = new ShareHolderCreatedEvent(shareHolder, ZonedDateTime.now());
        shareHolderMessagePublisher.publish(shareHolderCreatedEvent, SHAREHOLDER_CREATED);
        return shareHolderCreatedEvent;
    }

    @Override
    public PageInfo<ShareHolder> searchShareHolders(
            PageInfoPage pageInfoPage, PageInfoSize pageInfoSize, PageInfoField pageInfoField, PageInfoDirection pageInfoDirection, Keyword keyword) {
        validatePageParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        Assert.field("Keyword", keyword).notNull();
        PageInfo<ShareHolder> shareHolderPageInfo = shareHolderRepository.search(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
        LOGGER.info("Found " + shareHolderPageInfo.getTotalElements() + " shareholder for keyword " + keyword.text().value());
        return shareHolderPageInfo;
    }

    private void validateShareHolder(ShareHolder shareHolder) {
        validateAccountNumber(shareHolder.getId(), shareHolder.getAccountNumber());

        if(shareHolder.getBankAccountNumber() != null) {
            validateBankAccountNumber(shareHolder.getId(), shareHolder.getBankAccountNumber());
        }

        if(shareHolder.getPhone() != null) {
            validatePhone(shareHolder.getId(), shareHolder.getPhone());
        }

        if(shareHolder.getEmail() != null) {
            validateEmail(shareHolder.getId(), shareHolder.getEmail());
        }
    }

    private void validateEmail(ShareHolderId shareHolderId, Email email) {
        if(shareHolderId == null && shareHolderRepository.existsByEmail(email)) {
            throw new ShareHolderEmailConflictException(new String[] {email.text().value()});
        }

        Optional<ShareHolder> oldShareHolder = shareHolderRepository.findByEmail(email);
        if(shareHolderId != null && oldShareHolder.isPresent() && !oldShareHolder.get().getId().equals(shareHolderId)) {
            throw new ShareHolderEmailConflictException(new String[] {email.text().value()});
        }
    }

    private void validatePhone(ShareHolderId shareHolderId, Phone phone) {
        if(shareHolderId == null && shareHolderRepository.existsByPhone(phone)) {
            throw new ShareHolderPhoneConflictException(new String[] {phone.text().value()});
        }

        Optional<ShareHolder> oldShareHolder = shareHolderRepository.findByPhone(phone);
        if(shareHolderId != null && oldShareHolder.isPresent() && !oldShareHolder.get().getId().equals(shareHolderId)) {
            throw new ShareHolderPhoneConflictException(new String[] {phone.text().value()});
        }
    }

    private void validateBankAccountNumber(ShareHolderId shareHolderId, BankAccountNumber bankAccountNumber) {
        if(shareHolderId == null && shareHolderRepository.existsByBankAccountNumber(bankAccountNumber)) {
            throw new ShareHolderBankAccountNumberConflictException(new String[] {bankAccountNumber.text().value()});
        }

        Optional<ShareHolder> oldShareHolder = shareHolderRepository.findByBankAccountNumber(bankAccountNumber);
        if(shareHolderId != null && oldShareHolder.isPresent() && !oldShareHolder.get().getId().equals(shareHolderId)) {
            throw new ShareHolderBankAccountNumberConflictException(new String[] {bankAccountNumber.text().value()});
        }
    }

    private void validateAccountNumber(ShareHolderId shareHolderId, AccountNumber accountNumber) {
        if(shareHolderId == null && shareHolderRepository.existsByAccountNumber(accountNumber)) {
            throw new ShareHolderAccountNumberConflictException(new String[] {accountNumber.text().value()});
        }

        Optional<ShareHolder> oldShareHolder = shareHolderRepository.findByAccountNumber(accountNumber);
        if(shareHolderId != null && oldShareHolder.isPresent() && !oldShareHolder.get().getId().equals(shareHolderId)) {
            throw new ShareHolderAccountNumberConflictException(new String[] {accountNumber.text().value()});
        }
    }
}
