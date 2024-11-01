package cm.xenonbyte.gestitre.domain.company.ports.secondary;

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
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public interface CertificateTemplateRepository {
    @Nonnull CertificateTemplate save(@Nonnull CertificateTemplate certificateTemplate);

    Boolean existsByName(@Nonnull Name name);

    Boolean existsById(CertificateTemplateId certificateTemplateId);

    @Nonnull Optional<CertificateTemplate> findById(@Nonnull CertificateTemplateId certificateTemplateId);

    @Nonnull PageInfo<CertificateTemplate> findAll(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction);

    @Nonnull PageInfo<CertificateTemplate> search(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction, @Nonnull Keyword keyword);

    @Nonnull CertificateTemplate updateCertificateTemplate(@Nonnull CertificateTemplate oldCertificateTemplate, @Nonnull CertificateTemplate newCertificateTemplate);
}
