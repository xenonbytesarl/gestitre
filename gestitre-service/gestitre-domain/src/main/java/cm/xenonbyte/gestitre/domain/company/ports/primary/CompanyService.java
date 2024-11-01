package cm.xenonbyte.gestitre.domain.company.ports.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Direction;
import cm.xenonbyte.gestitre.domain.common.vo.Field;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Page;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Size;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public interface CompanyService {
    @Nonnull
    CompanyCreatedEvent createCompany(@Nonnull Company company);

    @Nonnull Company findCompanyById(@Nonnull CompanyId companyId);

    PageInfo<Company> findCompanies(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction);

    PageInfo<Company> searchCompanies(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction, @Nonnull Keyword keyword);

    @Nonnull CompanyUpdatedEvent updateCompany(@Nonnull CompanyId companyId, @Nonnull Company company);
}
