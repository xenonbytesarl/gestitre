package cm.xenonbyte.gestitre.domain.company.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Direction;
import cm.xenonbyte.gestitre.domain.common.vo.Field;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Page;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Size;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CertificateTemplateUpdateEvent;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public interface CertificateTemplateService {
    @Nonnull CertificateTemplateCreatedEvent createCertificate(@Nonnull CertificateTemplate certificateTemplate);
    @Nonnull CertificateTemplate findCertificateById(@Nonnull CertificateTemplateId certificateTemplateId);
    @Nonnull PageInfo<CertificateTemplate> findCertificates(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction);
    @Nonnull PageInfo<CertificateTemplate> searchCertificates(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction, @Nonnull Keyword keyword);
    @Nonnull
    CertificateTemplateUpdateEvent updateCertificates(@Nonnull CertificateTemplateId certificateTemplateId, @Nonnull CertificateTemplate certificateTemplate);
}
