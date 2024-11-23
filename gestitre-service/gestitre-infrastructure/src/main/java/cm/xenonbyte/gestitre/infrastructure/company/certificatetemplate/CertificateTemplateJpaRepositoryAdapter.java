package cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate;

import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static cm.xenonbyte.gestitre.infrastructure.common.InsfrastructureConstant.KEYWORD_PARAM;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@Named(value = "certificateTemplateRepository")
@ApplicationScoped
public final class CertificateTemplateJpaRepositoryAdapter implements CertificateTemplateRepository {

    private final CertificateTemplateJpaRepository certificateTemplateJpaRepository;
    private final CertificateTemplateJpaMapper certificateTemplateJpaMapper;
    private static final String CERTIFICATE_TEMPLATE_SEARCH_BY_KEYWORD_QUERY = "select ct from CertificateTemplateJpa ct where lower(ct.name) like lower(concat('%',:" + KEYWORD_PARAM + ",'%')) order by ct.";

    public CertificateTemplateJpaRepositoryAdapter(
            @Nonnull CertificateTemplateJpaRepository certificateTemplateJpaRepository,
            @Nonnull CertificateTemplateJpaMapper certificateTemplateJpaMapper
    ) {
        this.certificateTemplateJpaRepository = Objects.requireNonNull(certificateTemplateJpaRepository);
        this.certificateTemplateJpaMapper = Objects.requireNonNull(certificateTemplateJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public CertificateTemplate create(@Nonnull CertificateTemplate certificateTemplate) {
        certificateTemplateJpaRepository.persist(
                certificateTemplateJpaMapper.toCertificateTemplateJpa(certificateTemplate)
        );
        return certificateTemplate;
    }

    @Override
    public Boolean existsByName(@Nonnull Name name) {
        return certificateTemplateJpaRepository.existsByName(name.text().value());
    }

    @Override
    public Boolean existsById(CertificateTemplateId certificateTemplateId) {
        return certificateTemplateJpaRepository.findByIdOptional(certificateTemplateId.getValue())
                .isPresent();
    }

    @Nonnull
    @Override
    public Optional<CertificateTemplate> findById(@Nonnull CertificateTemplateId certificateTemplateId) {
        return certificateTemplateJpaRepository.findByIdOptional(certificateTemplateId.getValue())
                .map(certificateTemplateJpaMapper::toCertificateTemplate);
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> findAll(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection) {

        PanacheQuery<CertificateTemplateJpa> certificateTemplateQueryResult =
            certificateTemplateJpaRepository.findAll(
                Sort
                    .by(pageInfoField.text().value())
                    .direction(
                        pageInfoDirection.equals(PageInfoDirection.ASC)
                            ? Sort.Direction.Ascending
                            : Sort.Direction.Descending
                    )
            );
        PanacheQuery<CertificateTemplateJpa> certificateTemplatePageQueryResult =
                certificateTemplateQueryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));

        return new PageInfo<>(
                !certificateTemplatePageQueryResult.hasPreviousPage(),
                !certificateTemplatePageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                certificateTemplateQueryResult.count(),
                certificateTemplateQueryResult.pageCount(),
                certificateTemplatePageQueryResult
                    .list()
                    .stream()
                    .map(certificateTemplateJpaMapper::toCertificateTemplate)
                    .toList()
        );
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> search(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection,
            @Nonnull Keyword keyword) {

        String orderBy = pageInfoField.text().value() + " " + (pageInfoDirection.equals(PageInfoDirection.ASC) ? "asc" : "desc");
        PanacheQuery<CertificateTemplateJpa> certificateTemplateQueryResult =
                certificateTemplateJpaRepository.find(
                        CERTIFICATE_TEMPLATE_SEARCH_BY_KEYWORD_QUERY + orderBy,
                        Map.of(KEYWORD_PARAM, keyword.text().value())
                );
        PanacheQuery<CertificateTemplateJpa> certificateTemplatePageQueryResult =
                certificateTemplateQueryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));

        return new PageInfo<>(
                !certificateTemplatePageQueryResult.hasPreviousPage(),
                !certificateTemplatePageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                certificateTemplateQueryResult.count(),
                certificateTemplateQueryResult.pageCount(),
                certificateTemplatePageQueryResult
                        .list()
                        .stream()
                        .map(certificateTemplateJpaMapper::toCertificateTemplate)
                        .toList()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public CertificateTemplate update(@Nonnull CertificateTemplateId certificateTemplatId, @Nonnull CertificateTemplate newCertificateTemplate) {
        CertificateTemplateJpa oldCertificateTemplateJpa = certificateTemplateJpaRepository.findById(certificateTemplatId.getValue());
        CertificateTemplateJpa newCertificateTemplateJpa = certificateTemplateJpaMapper.toCertificateTemplateJpa(newCertificateTemplate);
        certificateTemplateJpaMapper.copyNewToOldCertificateTemplateJpa(newCertificateTemplateJpa, oldCertificateTemplateJpa);
        return certificateTemplateJpaMapper.toCertificateTemplate(oldCertificateTemplateJpa);
    }

    @Override
    public Optional<CertificateTemplate> findByName(@Nonnull Name name) {
        return certificateTemplateJpaRepository.findByName(name.text().value())
                .map(certificateTemplateJpaMapper::toCertificateTemplate);
    }
}
