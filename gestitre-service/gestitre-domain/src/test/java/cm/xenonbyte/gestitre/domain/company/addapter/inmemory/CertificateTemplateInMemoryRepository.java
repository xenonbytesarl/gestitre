package cm.xenonbyte.gestitre.domain.company.addapter.inmemory;

import cm.xenonbyte.gestitre.domain.common.vo.Direction;
import cm.xenonbyte.gestitre.domain.common.vo.Field;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Page;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Size;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CertificateTemplateRepository;
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
    public CertificateTemplate save(@Nonnull CertificateTemplate certificateTemplate) {
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
    public PageInfo<CertificateTemplate> findAll(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction) {
        PageInfo<CertificateTemplate> certificateTemplatePageInfo = new PageInfo<>();
        Comparator<CertificateTemplate> comparing = Comparator.comparing((CertificateTemplate certificateTemplate) -> certificateTemplate.getName().text().value());
        return certificateTemplatePageInfo.of(
                page.value(),
                size.value(),
                certificateTemplates.values().stream()
                        .sorted(direction.equals(Direction.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Nonnull
    @Override
    public PageInfo<CertificateTemplate> search(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction, @Nonnull Keyword keyword) {
        PageInfo<CertificateTemplate> certificateTemplatePageInfo = new PageInfo<>();
        Comparator<CertificateTemplate> comparing = Comparator.comparing((CertificateTemplate certificateTemplate) -> certificateTemplate.getName().text().value());
        return certificateTemplatePageInfo.of(
                page.value(),
                size.value(),
                certificateTemplates.values().stream()
                        .filter(certificateTemplate -> certificateTemplate.getName().text().value().contains(keyword.text().value()))
                        .sorted(direction.equals(Direction.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Nonnull
    @Override
    public CertificateTemplate updateCertificateTemplate(@Nonnull CertificateTemplate oldCertificateTemplate, @Nonnull CertificateTemplate newCertificateTemplate) {
        certificateTemplates.put(oldCertificateTemplate.getId(), newCertificateTemplate);
        return newCertificateTemplate;
    }


}
