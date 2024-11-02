package cm.xenonbyte.gestitre.domain.company.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public interface CertificateTemplateRepository {
    @Nonnull CertificateTemplate create(@Nonnull CertificateTemplate certificateTemplate);

    Boolean existsByName(@Nonnull Name name);

    Boolean existsById(CertificateTemplateId certificateTemplateId);

    @Nonnull Optional<CertificateTemplate> findById(@Nonnull CertificateTemplateId certificateTemplateId);

    @Nonnull PageInfo<CertificateTemplate> findAll(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection);

    @Nonnull PageInfo<CertificateTemplate> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword);

    @Nonnull CertificateTemplate update(@Nonnull CertificateTemplateId certificateTemplateId, @Nonnull CertificateTemplate newCertificateTemplate);
}
