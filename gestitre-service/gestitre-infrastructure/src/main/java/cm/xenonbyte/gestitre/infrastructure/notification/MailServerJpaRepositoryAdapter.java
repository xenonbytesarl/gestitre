package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailServerRepository;
import cm.xenonbyte.gestitre.domain.notification.vo.MailServerId;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Slf4j
@ApplicationScoped
public final class MailServerJpaRepositoryAdapter implements MailServerRepository {

    private static final String MAIL_SERVER_SEARCH_BY_KEYWORD_QUERY = "select ms from MailServerJpa ms where lower(concat(ms.name, '', ms.from, '', ms.type, '', ms.state, '', ms.host, '', cast(ms.port as text))) like lower(?1) order by ms.";

    private final MailServerJpaRepository mailServerJpaRepository;
    private final MailServerJpaMapper mailServerJpaMapper;

    public MailServerJpaRepositoryAdapter(
            @Nonnull MailServerJpaRepository mailServerJpaRepository,
            @Nonnull MailServerJpaMapper mailServerJpaMapper
    ) {
        this.mailServerJpaRepository = Objects.requireNonNull(mailServerJpaRepository);
        this.mailServerJpaMapper = Objects.requireNonNull(mailServerJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public MailServer create(@Nonnull MailServer mailServer) {
        mailServerJpaRepository.persist(mailServerJpaMapper.toMailServerJpa(mailServer));
        return mailServerJpaMapper.toMailServer(
                mailServerJpaRepository.findById(mailServer.getId().getValue())
        );
    }

    @Override
    public Boolean existByName(@Nonnull Name name) {
        return mailServerJpaRepository.existsByName(name.text().value());
    }

    @Override
    public Optional<MailServer> findById(@Nonnull MailServerId mailServerId) {
        return mailServerJpaRepository.findByIdOptional(mailServerId.getValue())
                .map(mailServerJpaMapper::toMailServer);
    }

    @Nonnull
    @Override
    public PageInfo<MailServer> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword) {
        PanacheQuery<MailServerJpa> queryResult = mailServerJpaRepository.find(
                MAIL_SERVER_SEARCH_BY_KEYWORD_QUERY + PageInfo.computeOderBy(field, direction), keyword.toLikeKeyword());
        PanacheQuery<MailServerJpa> mailServerPageQueryResult =
                queryResult.page(Page.of(page.value(), size.value()));
        return new PageInfo<>(
                !mailServerPageQueryResult.hasPreviousPage(),
                !mailServerPageQueryResult.hasNextPage(),
                size.value(),
                mailServerPageQueryResult.count(),
                mailServerPageQueryResult.pageCount(),
                mailServerPageQueryResult
                        .list()
                        .stream()
                        .map(mailServerJpaMapper::toMailServer)
                        .toList()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public MailServer update(@Nonnull MailServerId mailServerId, @Nonnull MailServer newMailServer) {
        MailServerJpa oldMailServerJpa = mailServerJpaRepository.findById(mailServerId.getValue());
        MailServerJpa newMailServerJpa = mailServerJpaMapper.toMailServerJpa(newMailServer);
        mailServerJpaMapper.copyNewToOldMailServerJpa(newMailServerJpa, oldMailServerJpa);
        return mailServerJpaMapper.toMailServer(oldMailServerJpa);
    }

    @Override
    public Optional<MailServer> findByIsDefault() {
        return mailServerJpaRepository.findByIsDefault()
                .map(mailServerJpaMapper::toMailServer);
    }
}
