package cm.xenonbyte.gestitre.domain.company.addapter.inmemory;

import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import jakarta.annotation.Nonnull;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CertificateTemplateInMemoryRepository implements CertificateTemplateRepository {

    private final Map<CertificateTemplateId, CertificateTemplate> certificateTemplates = new LinkedHashMap<>();

    @Nonnull
    @Override
    public CertificateTemplate create(@Nonnull CertificateTemplate certificateTemplate) {
        certificateTemplates.put(certificateTemplate.getId(), certificateTemplate);
        return certificateTemplate;
    }

    @Override
    public Boolean existsByName(@Nonnull Name name) {
        return certificateTemplates.values().stream()
                .anyMatch(certificateTemplate ->
                        certificateTemplate.getName().text().value().equalsIgnoreCase(name.text().value()));
    }

    @Override
    public Boolean existsById(CertificateTemplateId certificateTemplateId) {
        return certificateTemplates.containsKey(certificateTemplateId);
    }

    @Nonnull
    @Override
    public Optional<CertificateTemplate> findById(@Nonnull CertificateTemplateId certificateTemplateId) {
        CertificateTemplate certificateTemplate = certificateTemplates.get(certificateTemplateId);
        return certificateTemplate == null? Optional.empty(): Optional.of(certificateTemplate);
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> findAll(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection) {
        PageInfo<CertificateTemplate> certificateTemplatePageInfo = new PageInfo<>();
        Comparator<CertificateTemplate> comparing = Comparator.comparing((CertificateTemplate certificateTemplate) -> certificateTemplate.getName().text().value());
        return certificateTemplatePageInfo.of(
                pageInfoPage.value(),
                pageInfoSize.value(),
                certificateTemplates.values().stream()
                        .sorted(pageInfoDirection.equals(PageInfoDirection.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword) {
        PageInfo<CertificateTemplate> certificateTemplatePageInfo = new PageInfo<>();
        Comparator<CertificateTemplate> comparing = Comparator.comparing((CertificateTemplate certificateTemplate) -> certificateTemplate.getName().text().value());
        return certificateTemplatePageInfo.of(
                pageInfoPage.value(),
                pageInfoSize.value(),
                certificateTemplates.values().stream()
                        .filter(certificateTemplate -> certificateTemplate.getName().text().value().contains(keyword.text().value()))
                        .sorted(pageInfoDirection.equals(PageInfoDirection.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Nonnull
    @Override
    public CertificateTemplate update(@Nonnull CertificateTemplateId certificateTemplateId, @Nonnull CertificateTemplate newCertificateTemplate) {
        certificateTemplates.put(certificateTemplateId, newCertificateTemplate);
        return newCertificateTemplate;
    }

    @Override
    public Optional<CertificateTemplate> findByName(@Nonnull Name name) {
        return certificateTemplates.values().stream().filter(certificateTemplate ->
                        certificateTemplate.getName().text().value().equalsIgnoreCase(name.text().value()))
                .findFirst();
    }


}
