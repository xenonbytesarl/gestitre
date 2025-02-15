package cm.xenonbyte.gestitre.domain.company.addapter.inmemory;

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
public final class CompanyInMemoryRepository implements CompanyRepository {

    private final Map<CompanyId, Company> companies = new LinkedHashMap();
    @Nonnull
    @Override
    public Company create(@Nonnull Company company) {
        companies.put(company.getId(), company);
        return company;
    }

    @Override
    public Boolean existsByCompanyName(CompanyName companyName) {
        return companies.values().stream().
                anyMatch(company ->
                        company.getCompanyName().text().value().equalsIgnoreCase(companyName.text().value()));
    }

    @Override
    public Optional<Company> findById(@Nonnull CompanyId companyId) {
        Company company = companies.get(companyId);
        return company == null? Optional.empty(): Optional.of(company);
    }

    @Override
    public PageInfo<Company> findAll(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection) {
        PageInfo<Company> companyPageInfo = new PageInfo<>();
        Comparator<Company> comparing = Comparator.comparing((Company company) -> company.getCompanyName().text().value());
        return companyPageInfo.of(
                pageInfoPage.value(),
                pageInfoSize.value(),
                companies.values().stream()
                        .sorted(pageInfoDirection.equals(PageInfoDirection.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Override
    public PageInfo<Company> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword) {
        PageInfo<Company> companyPageInfo = new PageInfo<>();
        Comparator<Company> comparing = Comparator.comparing((Company company) -> company.getCompanyName().text().value());
        return companyPageInfo.of(
                pageInfoPage.value(),
                pageInfoSize.value(),
                companies.values().stream()
                        .filter(company -> company.getCompanyName().text().value().contains(keyword.text().value()))
                        .sorted(pageInfoDirection.equals(PageInfoDirection.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Override
    public Boolean existsByEmail(@Nonnull Email email) {
        return companies.values().stream()
                .anyMatch(company -> company.getContact().email().text().value().equalsIgnoreCase(email.text().value()));
    }

    @Override
    public Boolean existsByPhone(@Nonnull Phone phone) {
        return companies.values().stream()
                .anyMatch(company -> company.getContact().phone() != null && company.getContact().phone().text().value().equalsIgnoreCase(phone.text().value()));
    }

    @Override
    public Optional<Company> findByPhone(@Nonnull Phone phone) {
        return companies.values().stream().filter(company ->
                company.getContact().phone() != null && company.getContact().phone().text().value().replaceAll("\\s", "")
                        .equals(phone.text().value().replaceAll("\\s", ""))).
                findFirst();
    }

    @Override
    public Optional<Company> findByEmail(@Nonnull Email email) {
        return companies.values().stream().filter(company ->
                company.getContact().email().text().value().equals(email.text().value()))
                .findFirst();
    }

    @Override
    public Optional<Company> findByCompanyName(@Nonnull CompanyName companyName) {
        return companies.values().stream().filter(company ->
                company.getCompanyName().text().value().equalsIgnoreCase(companyName.text().value()))
                .findFirst();
    }

    @Nonnull
    @Override
    public Company update(@Nonnull CompanyId companyId, @Nonnull Company newCompany) {
        companies.replace(companyId, newCompany);
        return newCompany;
    }

    @Override
    public Boolean existByRegistrationNumber(@Nonnull RegistrationNumber registrationNumber) {
        return companies.values().stream().anyMatch(company ->
                company.getRegistrationNumber() != null && company.getRegistrationNumber().text().value().equalsIgnoreCase(registrationNumber.text().value()));
    }

    @Override
    public Optional<Company> findByRegistrationNumber(@Nonnull RegistrationNumber registrationNumber) {
        return companies.values().stream().filter(company ->
                company.getRegistrationNumber() != null && company.getRegistrationNumber().text().value().equalsIgnoreCase(registrationNumber.text().value()))
                .findFirst();
    }

    @Override
    public Boolean existByTaxNumber(@Nonnull TaxNumber taxNumber) {
        return companies.values().stream().anyMatch(company ->
                company.getTaxNumber() != null && company.getTaxNumber().text().value().equalsIgnoreCase(taxNumber.text().value()));
    }

    @Override
    public Optional<Company> findByTaxNumber(@Nonnull TaxNumber taxNumber) {
        return companies.values().stream().filter(company ->
                company.getTaxNumber() != null && company.getTaxNumber().text().value().equalsIgnoreCase(taxNumber.text().value()))
                .findFirst();
    }

    @Override
    public Boolean existByIsinCode(IsinCode isinCode) {
        return companies.values().stream().anyMatch(company ->
                company.getIsinCode() != null && company.getIsinCode().text().value().equalsIgnoreCase(isinCode.text().value()));
    }

    @Override
    public Optional<Company> findByIsinCode(@Nonnull IsinCode isinCode) {
        return companies.values().stream().filter(company ->
                company.getIsinCode() != null && company.getIsinCode().text().value().equalsIgnoreCase(isinCode.text().value()))
                .findFirst();
    }

    @Override
    public Boolean existByWebSiteUrl(@Nonnull WebSiteUrl webSiteUrl) {
        return companies.values().stream().anyMatch(company ->
                company.getWebSiteUrl() != null && company.getWebSiteUrl().text().value().equalsIgnoreCase(webSiteUrl.text().value()));
    }

    @Override
    public Optional<Company> findByWebSiteUrl(@Nonnull WebSiteUrl webSiteUrl) {
        return companies.values().stream().filter(company ->
                company.getWebSiteUrl() != null && company.getWebSiteUrl().text().value().equalsIgnoreCase(webSiteUrl.text().value()))
                .findFirst();
    }

    @Override
    public Boolean existsById(@Nonnull CompanyId companyId) {
        return companies.containsKey(companyId);
    }

    @Override
    public Boolean existsByFax(@Nonnull Fax fax) {
        return companies.values().stream().anyMatch(company ->
                company.getContact().fax() != null && company.getContact().fax().text().value().equalsIgnoreCase(fax.text().value()));
    }

    @Override
    public Optional<Company> findByFax(@Nonnull Fax fax) {
        return companies.values().stream().filter(company ->
                company.getContact().fax() != null && company.getContact().fax().text().value().equalsIgnoreCase(fax.text().value()))
                .findFirst();
    }

    @Override
    public Optional<Company> findByTenantId(@Nonnull TenantId tenantId) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByCode(@Nonnull Code code) {
        return companies.values().stream().anyMatch(company ->
                company.getCode().text().value().equalsIgnoreCase(code.text().value()));
    }

    @Override
    public Optional<Company> findByCode(@Nonnull Code code) {
        return companies.values().stream().filter(company ->
                company.getCode().text().value().equalsIgnoreCase(code.text().value())).findFirst();
    }
}
