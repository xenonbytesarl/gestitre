package cm.xenonbyte.gestitre.domain.shareholder.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.vo.AccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
public interface ShareHolderRepository {
    Boolean existsByAccountNumber(@Nonnull AccountNumber accountNumber);

    Optional<ShareHolder> findByAccountNumber(@Nonnull AccountNumber accountNumber);

    Boolean existsByBankAccountNumber(@Nonnull BankAccountNumber bankAccountNumber);

    Optional<ShareHolder> findByBankAccountNumber(@Nonnull BankAccountNumber bankAccountNumber);

    Boolean existsByPhone(@Nonnull Phone phone);

    Optional<ShareHolder> findByPhone(@Nonnull Phone phone);

    Boolean existsByEmail(@Nonnull Email email);

    Optional<ShareHolder> findByEmail(@Nonnull Email email);

    @Nonnull ShareHolder create(@Nonnull ShareHolder shareHolder);

    @Nonnull PageInfo<ShareHolder> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword);

    Optional<ShareHolder> findById(@Nonnull ShareHolderId shareHolderId);

    @Nonnull ShareHolder update(@Nonnull ShareHolderId shareHolderId, @Nonnull ShareHolder newShareHolder);
}
