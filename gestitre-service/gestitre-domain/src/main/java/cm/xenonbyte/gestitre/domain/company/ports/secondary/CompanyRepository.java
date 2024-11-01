package cm.xenonbyte.gestitre.domain.company.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.Direction;
import cm.xenonbyte.gestitre.domain.common.vo.Field;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Page;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Size;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public interface CompanyRepository {
    @Nonnull
    Company save(@Nonnull Company company);

    Boolean existsByCompanyName(CompanyName companyName);

    Optional<Company> findById(@Nonnull CompanyId companyId);

    PageInfo<Company> findAll(@Nonnull Page page,@Nonnull Size size,@Nonnull Field field,@Nonnull Direction direction);

    PageInfo<Company> search(@Nonnull Page page,@Nonnull Size size,@Nonnull Field field,@Nonnull Direction direction,@Nonnull Keyword keyword);

    Boolean existsByEmail(@Nonnull Email email);

    Boolean existsByPhone(@Nonnull Phone phone);

    Optional<Company> findCompanyByPhone(@Nonnull Phone phone);

    Optional<Company> findCompanyByEmail(@Nonnull Email email);

    Optional<Company> findCompanyByCompanyName(@Nonnull CompanyName companyName);

    @Nonnull Company update(@Nonnull Company oldCompany, @Nonnull Company newCompany);
}
