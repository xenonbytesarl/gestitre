package cm.xenonbyte.gestitre.domain.company.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
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
    Company create(@Nonnull Company company);

    Boolean existsByCompanyName(CompanyName companyName);

    Optional<Company> findById(@Nonnull CompanyId companyId);

    PageInfo<Company> findAll(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection);

    PageInfo<Company> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword);

    Boolean existsByEmail(@Nonnull Email email);

    Boolean existsByPhone(@Nonnull Phone phone);

    Optional<Company> findByPhone(@Nonnull Phone phone);

    Optional<Company> findByEmail(@Nonnull Email email);

    Optional<Company> findByCompanyName(@Nonnull CompanyName companyName);

    @Nonnull Company update(@Nonnull CompanyId companyId, @Nonnull Company newCompany);
}
