package cm.xenonbyte.gestitre.infrastructure.company;

import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CompanyRepository;
import cm.xenonbyte.gestitre.domain.company.vo.IsinCode;
import cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber;
import cm.xenonbyte.gestitre.domain.company.vo.TaxNumber;
import cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Fax;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static cm.xenonbyte.gestitre.infrastructure.common.InsfrastructureConstant.KEYWORD_PARAM;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@Slf4j
@ApplicationScoped
public final class CompanyJpaRepositoryAdapter implements CompanyRepository {

    private static final String COMPANY_SEARCH_BY_KEYWORD_QUERY = "select c from CompanyJpa c left join c.certificateTemplateJpa ct where lower(concat(c.companyName, '', c.companyManagerName, '', c.licenceJpa, '', c.legalFormJpa, '', c.addressJpa.zipCode, '', \n" +
                            "c.addressJpa.country, '', c.contactJpa.email, '', c.contactJpa.name, '', c.addressJpa.city, '', coalesce(c.addressJpa.street, ''), '', coalesce(c.activity,''), '', \n" +
                            "coalesce(replace(c.contactJpa.phone, ' ', ''), ''), '', coalesce(replace(c.contactJpa.fax, ' ', ''), ''), '', coalesce(webSiteUrl, ''), '', coalesce(c.registrationNumber, ''), '', coalesce(c.isinCode, ''), '', \n" +
                            "coalesce(c.taxNumber, ''), '', coalesce(cast(c.grossDividendStockUnit as text), ''), '', coalesce(cast(c.nominalValue as text), ''), '', coalesce(cast(c.stockQuantity as text), ''), '', coalesce(ct.name, ''))) like lower(concat('%',:" + KEYWORD_PARAM + ",'%')) order by c.";

    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyJpaMapper companyJpaMapper;

    public CompanyJpaRepositoryAdapter(
            @Nonnull final CompanyJpaRepository companyJpaRepository,
            @Nonnull final CompanyJpaMapper companyJpaMapper) {
        this.companyJpaRepository = Objects.requireNonNull(companyJpaRepository);
        this.companyJpaMapper = Objects.requireNonNull(companyJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public Company create(@Nonnull Company company) {
        companyJpaRepository.persist(companyJpaMapper.toCompanyJpa(company));
        return companyJpaMapper.toCompany(
                companyJpaRepository.findById(company.getId().getValue())
        );
    }

    @Override
    public Boolean existsByCompanyName(CompanyName companyName) {
        return companyJpaRepository.existsByCompanyName(companyName.text().value());
    }

    @Override
    public Optional<Company> findById(@Nonnull CompanyId companyId) {
        return companyJpaRepository.findByIdOptional(companyId.getValue())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public PageInfo<Company> findAll(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection) {
        PanacheQuery<CompanyJpa> companyQueryResult = companyJpaRepository.findAll(
                Sort
                        .by(pageInfoField.text().value())
                        .direction(
                                pageInfoDirection.equals(PageInfoDirection.ASC)
                                        ? Sort.Direction.Ascending
                                        : Sort.Direction.Descending
                        )
        );
        PanacheQuery<CompanyJpa> companyPageQueryResult =
                companyQueryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));
        return new PageInfo<>(
                !companyPageQueryResult.hasPreviousPage(),
                !companyPageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                companyQueryResult.count(),
                companyQueryResult.pageCount(),
                companyPageQueryResult
                        .list()
                        .stream()
                        .map(companyJpaMapper::toCompany)
                        .toList()
        );
    }

    @Override
    public PageInfo<Company> search(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection,
            @Nonnull Keyword keyword) {
        String orderBy = pageInfoField.text().value() + " " + (pageInfoDirection.equals(PageInfoDirection.ASC) ? "asc" : "desc");
        PanacheQuery<CompanyJpa> queryResult =
                companyJpaRepository.find(
                        COMPANY_SEARCH_BY_KEYWORD_QUERY + orderBy,
                        Map.of(KEYWORD_PARAM, keyword.text().value())
                );
        PanacheQuery<CompanyJpa> companyPageQueryResult =
                queryResult.page(Page.of(pageInfoPage.value(), pageInfoSize.value()));
        return new PageInfo<>(
                !companyPageQueryResult.hasPreviousPage(),
                !companyPageQueryResult.hasNextPage(),
                pageInfoSize.value(),
                companyPageQueryResult.count(),
                companyPageQueryResult.pageCount(),
                companyPageQueryResult
                        .list()
                        .stream()
                        .map(companyJpaMapper::toCompany)
                        .toList()
        );
    }

    @Override
    public Boolean existsByEmail(@Nonnull Email email) {
        return companyJpaRepository.existsByEmail(email.text().value());
    }

    @Override
    public Boolean existsByPhone(@Nonnull Phone phone) {
        return companyJpaRepository.existsByPhone(phone.text().value());
    }

    @Override
    public Optional<Company> findByPhone(@Nonnull Phone phone) {
        return companyJpaRepository.findByPhone(phone.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Optional<Company> findByEmail(@Nonnull Email email) {
        return companyJpaRepository.findByEmail(email.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Optional<Company> findByCompanyName(@Nonnull CompanyName companyName) {
        return companyJpaRepository.findCompanyName(companyName.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Nonnull
    @Override
    @Transactional
    public Company update(@Nonnull CompanyId companyId, @Nonnull Company newCompany) {
        CompanyJpa oldCompanyJpa = companyJpaRepository.findById(companyId.getValue());
        CompanyJpa newCompanyJpa = companyJpaMapper.toCompanyJpa(newCompany);
        companyJpaMapper.copyNewToOldCompanyJpa(newCompanyJpa, oldCompanyJpa);
        return companyJpaMapper.toCompany(oldCompanyJpa);
    }

    @Override
    public Boolean existByRegistrationNumber(@Nonnull RegistrationNumber registrationNumber) {
        return companyJpaRepository.existsByRegistrationNumber(registrationNumber.text().value());
    }

    @Override
    public Optional<Company> findByRegistrationNumber(@Nonnull RegistrationNumber registrationNumber) {
        return companyJpaRepository.findByRegistrationNumber(registrationNumber.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Boolean existByTaxNumber(@Nonnull TaxNumber taxNumber) {
        return companyJpaRepository.existsTaxNumber(taxNumber.text().value());
    }

    @Override
    public Optional<Company> findByTaxNumber(@Nonnull TaxNumber taxNumber) {
        return companyJpaRepository.findTaxNumber(taxNumber.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Boolean existByIsinCode(IsinCode isinCode) {
        return companyJpaRepository.existsByIsinCode(isinCode.text().value());
    }

    @Override
    public Optional<Company> findByIsinCode(@Nonnull IsinCode isinCode) {
        return companyJpaRepository.findByIsinCode(isinCode.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Boolean existByWebSiteUrl(@Nonnull WebSiteUrl webSiteUrl) {
        return companyJpaRepository.existsByWebSiteUrl(webSiteUrl.text().value());
    }

    @Override
    public Optional<Company> findByWebSiteUrl(@Nonnull WebSiteUrl webSiteUrl) {
        return companyJpaRepository.findByWebSiteUrl(webSiteUrl.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Boolean existsById(@Nonnull CompanyId companyId) {
        return companyJpaRepository.findByIdOptional(companyId.getValue()).isPresent();
    }

    @Override
    public Boolean existsByFax(@Nonnull Fax fax) {
        return companyJpaRepository.existsByFax(fax.text().value());
    }

    @Override
    public Optional<Company> findByFax(@Nonnull Fax fax) {
        return companyJpaRepository.findByFax(fax.text().value())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Optional<Company> findByTenantId(@Nonnull TenantId tenantId) {
        return companyJpaRepository.findByTenantId(tenantId.getValue())
                .map(companyJpaMapper::toCompany);
    }

    @Override
    public Boolean existsByCode(@Nonnull Code code) {
        return companyJpaRepository.existsByCode(code.text().value());
    }

    @Override
    public Optional<Company> findByCode(@Nonnull Code code) {
        return companyJpaRepository.findByCode(code.text().value())
                .map(companyJpaMapper::toCompany);
    }
}
