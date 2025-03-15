package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailTemplateRepository;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateId;
import cm.xenonbyte.gestitre.domain.notification.vo.MailTemplateType;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailTemplateJpaRepositoryAdapter implements MailTemplateRepository {

    private static final String MAIL_TEMPLATE_SEARCH_BY_KEYWORD_QUERY = "select mt from MailTemplateJpa mt where lower(concat(mt.name, '', mt.type)) like lower(?1) order by mt.";

    private final MailTemplateJpaRepository mailTemplateJpaRepository;
    private final MailTemplateJpaMapper mailTemplateJpaMapper;

    public MailTemplateJpaRepositoryAdapter(MailTemplateJpaRepository mailTemplateJpaRepository, MailTemplateJpaMapper mailTemplateJpaMapper) {
        this.mailTemplateJpaRepository = requireNonNull(mailTemplateJpaRepository);
        this.mailTemplateJpaMapper = requireNonNull(mailTemplateJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public MailTemplate save(@Nonnull MailTemplate mailTemplate) {
        mailTemplateJpaRepository.persist(mailTemplateJpaMapper.toMailTemplateJpa(mailTemplate));
        return mailTemplateJpaMapper.toMailTemplate(
                mailTemplateJpaRepository.findById(mailTemplate.getId().getValue())
        );
    }

    @Override
    public Optional<MailTemplate> findByType(@Nonnull MailTemplateType type) {
        return mailTemplateJpaRepository.findByType(MailTemplateTypeJpa.valueOf(type.name()))
                .map(mailTemplateJpaMapper::toMailTemplate);
    }

    @Override
    public Boolean existsByType(@Nonnull MailTemplateType type) {
        return mailTemplateJpaRepository.existsByType(MailTemplateTypeJpa.valueOf(type.name()));
    }

    @Override
    public Boolean existsByName(@Nonnull Name name) {
        return mailTemplateJpaRepository.existsByName(name.text().value());
    }

    @Override
    public Optional<MailTemplate> findByName(@Nonnull Name name) {
        return mailTemplateJpaRepository.findByName(name.text().value())
                .map(mailTemplateJpaMapper::toMailTemplate);
    }

    @Nonnull
    @Override
    public PageInfo<MailTemplate> search(@Nonnull PageInfoPage page, @Nonnull PageInfoSize size, @Nonnull PageInfoField field, @Nonnull PageInfoDirection direction, @Nonnull Keyword keyword) {
        PanacheQuery<MailTemplateJpa> queryResult = mailTemplateJpaRepository.find(
                MAIL_TEMPLATE_SEARCH_BY_KEYWORD_QUERY + PageInfo.computeOderBy(field, direction), keyword.toLikeKeyword());
        PanacheQuery<MailTemplateJpa> mailTemplatePageQueryResult =
                queryResult.page(Page.of(page.value(), size.value()));
        return new PageInfo<>(
                !mailTemplatePageQueryResult.hasPreviousPage(),
                !mailTemplatePageQueryResult.hasNextPage(),
                size.value(),
                mailTemplatePageQueryResult.count(),
                mailTemplatePageQueryResult.pageCount(),
                mailTemplatePageQueryResult
                        .list()
                        .stream()
                        .map(mailTemplateJpaMapper::toMailTemplate)
                        .toList()
        );
    }

    @Override
    public Optional<MailTemplate> findById(@Nonnull MailTemplateId mailTemplateId) {
        return mailTemplateJpaRepository.findByIdOptional(mailTemplateId.getValue())
                .map(mailTemplateJpaMapper::toMailTemplate);
    }

    @Nonnull
    @Override
    @Transactional
    public MailTemplate update(@Nonnull MailTemplateId mailTemplateId, @Nonnull MailTemplate newMailTemplate) {
        MailTemplateJpa oldMailTemplateJpa = mailTemplateJpaRepository.findById(mailTemplateId.getValue());
        MailTemplateJpa newMailTemplateJpa = mailTemplateJpaMapper.toMailTemplateJpa(newMailTemplate);
        mailTemplateJpaMapper.copyNewToOldMailTemplateJpa(newMailTemplateJpa, oldMailTemplateJpa);
        return mailTemplateJpaMapper.toMailTemplate(oldMailTemplateJpa);
    }
}
