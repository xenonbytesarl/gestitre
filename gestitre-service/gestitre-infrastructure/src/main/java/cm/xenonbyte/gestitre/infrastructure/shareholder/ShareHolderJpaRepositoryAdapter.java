package cm.xenonbyte.gestitre.infrastructure.shareholder;

import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.ports.secondary.ShareHolderRepository;
import cm.xenonbyte.gestitre.domain.shareholder.vo.AccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@Slf4j
@ApplicationScoped
public final class ShareHolderJpaRepositoryAdapter implements ShareHolderRepository {

    private final ShareHolderJpaRepository shareHolderJpaRepository;
    private final ShareHolderJpaMapper shareHolderJpaMapper;

    public ShareHolderJpaRepositoryAdapter(@Nonnull ShareHolderJpaRepository shareHolderJpaRepository,
                                           @Nonnull ShareHolderJpaMapper shareHolderJpaMapper) {
        this.shareHolderJpaRepository = Objects.requireNonNull(shareHolderJpaRepository);
        this.shareHolderJpaMapper = Objects.requireNonNull(shareHolderJpaMapper);
    }

    @Override
    public Boolean existsByAccountNumber(@Nonnull AccountNumber accountNumber) {
        return shareHolderJpaRepository.existsByAccountNumber(accountNumber.text().value());
    }

    @Override
    public Optional<ShareHolder> findByAccountNumber(@Nonnull AccountNumber accountNumber) {
        return shareHolderJpaRepository.findByAccountNumber(accountNumber.text().value())
                .map(shareHolderJpaMapper::toShareHolder);
    }

    @Override
    public Boolean existsByBankAccountNumber(@Nonnull BankAccountNumber bankAccountNumber) {
        return shareHolderJpaRepository.existsByBankAccountNumber(bankAccountNumber.text().value());
    }

    @Override
    public Optional<ShareHolder> findByBankAccountNumber(@Nonnull BankAccountNumber bankAccountNumber) {
        return shareHolderJpaRepository.findByBankAccountNumber(bankAccountNumber.text().value())
                .map(shareHolderJpaMapper::toShareHolder);
    }

    @Override
    public Boolean existsByPhone(@Nonnull Phone phone) {
        return shareHolderJpaRepository.existsByPhone(phone.text().value());
    }

    @Override
    public Optional<ShareHolder> findByPhone(@Nonnull Phone phone) {
        return shareHolderJpaRepository.findByPhone(phone.text().value())
                .map(shareHolderJpaMapper::toShareHolder);
    }

    @Override
    public Boolean existsByEmail(@Nonnull Email email) {
        return shareHolderJpaRepository.existsByEmail(email.text().value());
    }

    @Override
    public Optional<ShareHolder> findByEmail(@Nonnull Email email) {
        return shareHolderJpaRepository.findByEmail(email.text().value())
                .map(shareHolderJpaMapper::toShareHolder);
    }

    @Nonnull
    @Override
    @Transactional
    public ShareHolder create(@Nonnull ShareHolder shareHolder) {
        shareHolderJpaRepository.persist(
                shareHolderJpaMapper.toShareHolderJpa(shareHolder)
        );
        return shareHolderJpaMapper.toShareHolder(
                shareHolderJpaRepository.findById(shareHolder.getId().getValue())
        );
    }

    @Nonnull
    @Override
    public PageInfo<ShareHolder> findAll(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection,
            @Nonnull Keyword keyword) {
        PanacheQuery<ShareHolderJpa> shareholderQueryResult = shareHolderJpaRepository.findAll(
                Sort
                        .by(pageInfoField.text().value())
                        .direction(
                                pageInfoDirection.equals(PageInfoDirection.ASC)
                                        ? Sort.Direction.Ascending
                                        : Sort.Direction.Descending
                        )
        );
        PanacheQuery<ShareHolderJpa> shareholderPageQueryResult =
                shareholderQueryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));
        return new PageInfo<>(
                !shareholderPageQueryResult.hasPreviousPage(),
                !shareholderPageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                shareholderQueryResult.count(),
                shareholderQueryResult.pageCount(),
                shareholderPageQueryResult
                        .list()
                        .stream()
                        .map(shareHolderJpaMapper::toShareHolder)
                        .toList()
        );
    }
}
