package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.Mail;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailRepository;
import cm.xenonbyte.gestitre.domain.notification.vo.MailId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailState;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailJpaRepositoryAdapter implements MailRepository {

    private static final String MAIL_SEARCH_BY_KEYWORD_QUERY = "select m from MailServerJpa m left join MailTemplateJpa mt left join MailServerJpa ms where lower(concat(m.from, '', m.to, '', m.state, '', m.type, '', mt.name, '', ms.name)) like lower(?1) order by m.";

    private final MailJpaRepository mailJpaRepository;
    private final MailJpaMapper mailJpaMapper;

    public MailJpaRepositoryAdapter(MailJpaRepository mailJpaRepository, MailJpaMapper mailJpaMapper) {
        this.mailJpaRepository = requireNonNull(mailJpaRepository);
        this.mailJpaMapper = requireNonNull(mailJpaMapper);
    }

    @Nonnull
    @Override
    public Mail save(@Nonnull Mail mail) {
        mailJpaRepository.persist(mailJpaMapper.toMailJpa(mail));
        return mailJpaMapper.toMail(mailJpaRepository.findById(mail.getId().getValue()));
    }

    @Override
    public Optional<Mail> findById(@Nonnull MailId mailId) {
        return mailJpaRepository.findByIdOptional(mailId.getValue())
                .map(mailJpaMapper::toMail);
    }

    @Nonnull
    @Override
    public PageInfo<Mail> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword) {
        PanacheQuery<MailJpa> queryResult = mailJpaRepository.find(
                MAIL_SEARCH_BY_KEYWORD_QUERY + PageInfo.computeOderBy(field, direction), keyword.toLikeKeyword());
        PanacheQuery<MailJpa> mailPageQueryResult =
                queryResult.page(Page.of(page.value(), size.value()));
        return new PageInfo<>(
                !mailPageQueryResult.hasPreviousPage(),
                !mailPageQueryResult.hasNextPage(),
                size.value(),
                mailPageQueryResult.count(),
                mailPageQueryResult.pageCount(),
                mailPageQueryResult
                        .list()
                        .stream()
                        .map(mailJpaMapper::toMail)
                        .toList()
        );
    }

    @Override
    public List<Mail> findByState(@Nonnull MailState mailState) {
        return mailJpaRepository.findByState(MailStateJpa.valueOf(mailState.name()))
                .stream().map(mailJpaMapper::toMail)
                .toList();
    }

    @Nonnull
    @Override
    @Transactional
    public Mail update(@Nonnull MailId mailId, @Nonnull Mail newMail) {
        MailJpa oldMailJpa = mailJpaRepository.findById(mailId.getValue());
        MailJpa newmailJpa = mailJpaMapper.toMailJpa(newMail);
        mailJpaMapper.copyNewToOldMailJpa(newmailJpa, oldMailJpa);
        return mailJpaMapper.toMail(oldMailJpa);
    }
}
