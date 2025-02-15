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
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.computeOderBy;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@Slf4j
@ApplicationScoped
public final class ShareHolderJpaRepositoryAdapter implements ShareHolderRepository {

    private static final String SHARE_HOLDER_SEARCH_BY_KEYWORD_QUERY = "select sh from ShareHolderJpa sh where lower(concat(sh.name, '', sh.accountNumber, '', coalesce(sh.bankAccountNumber,''), '', cast(sh.initialBalance as text), '', cast(sh.createdDate as text), '', sh.taxResidence)) like lower(?1) order by sh.";


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
    public PageInfo<ShareHolder> search(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection,
            @Nonnull Keyword keyword) {
        PanacheQuery<ShareHolderJpa> queryResult =
                shareHolderJpaRepository.find(SHARE_HOLDER_SEARCH_BY_KEYWORD_QUERY + computeOderBy(pageInfoField, pageInfoDirection), keyword.toLikeKeyword());
        PanacheQuery<ShareHolderJpa> shareHolderPageQueryResult =
                queryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));
        return new PageInfo<>(
                !shareHolderPageQueryResult.hasPreviousPage(),
                !shareHolderPageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                shareHolderPageQueryResult.count(),
                shareHolderPageQueryResult.pageCount(),
                shareHolderPageQueryResult
                        .list()
                        .stream()
                        .map(shareHolderJpaMapper::toShareHolder)
                        .toList()
        );
    }

    @Override
    public Optional<ShareHolder> findById(@Nonnull ShareHolderId shareHolderId) {
        return shareHolderJpaRepository.findByIdOptional(shareHolderId.getValue())
                .map(shareHolderJpaMapper::toShareHolder);
    }


    @Override
    @Transactional
    public ShareHolder update(@Nonnull ShareHolderId shareHolderId, @Nonnull ShareHolder newShareHolder) {
        ShareHolderJpa oldShareHolderJpa = shareHolderJpaRepository.findById(shareHolderId.getValue());
        ShareHolderJpa newShareHolderJpa = shareHolderJpaMapper.toShareHolderJpa(newShareHolder);
        shareHolderJpaMapper.copyNewToOldShareHolderMapper(newShareHolderJpa, oldShareHolderJpa);
        return shareHolderJpaMapper.toShareHolder(oldShareHolderJpa);
    }
}
