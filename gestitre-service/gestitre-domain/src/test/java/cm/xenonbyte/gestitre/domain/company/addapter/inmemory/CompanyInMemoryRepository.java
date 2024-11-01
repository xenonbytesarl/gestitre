package cm.xenonbyte.gestitre.domain.company.addapter.inmemory;

import cm.xenonbyte.gestitre.domain.common.vo.Direction;
import cm.xenonbyte.gestitre.domain.common.vo.Field;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Page;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Size;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CompanyRepository;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
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
    public Company save(@Nonnull Company company) {
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
    public PageInfo<Company> findAll(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction) {
        PageInfo<Company> companyPageInfo = new PageInfo<>();
        Comparator<Company> comparing = Comparator.comparing((Company company) -> company.getCompanyName().text().value());
        return companyPageInfo.of(
                page.value(),
                size.value(),
                companies.values().stream()
                        .sorted(direction.equals(Direction.ASC) ? comparing: comparing.reversed())
                        .toList()
        );
    }

    @Override
    public PageInfo<Company> search(@Nonnull Page page, @Nonnull Size size, @Nonnull Field field, @Nonnull Direction direction, @Nonnull Keyword keyword) {
        PageInfo<Company> companyPageInfo = new PageInfo<>();
        Comparator<Company> comparing = Comparator.comparing((Company company) -> company.getCompanyName().text().value());
        return companyPageInfo.of(
                page.value(),
                size.value(),
                companies.values().stream()
                        .filter(company -> company.getCompanyName().text().value().contains(keyword.text().value()))
                        .sorted(direction.equals(Direction.ASC) ? comparing: comparing.reversed())
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
                .anyMatch(company -> company.getContact().phone().text().value().equalsIgnoreCase(phone.text().value()));
    }

    @Override
    public Optional<Company> findCompanyByPhone(@Nonnull Phone phone) {
        return companies.values().stream().filter(company ->
                company.getContact().phone().text().value().replaceAll("\\s", "")
                        .equals(phone.text().value().replaceAll("\\s", ""))).
                findFirst();
    }

    @Override
    public Optional<Company> findCompanyByEmail(@Nonnull Email email) {
        return companies.values().stream().filter(company ->
                company.getContact().email().text().value().equals(email.text().value()))
                .findFirst();
    }

    @Override
    public Optional<Company> findCompanyByCompanyName(@Nonnull CompanyName companyName) {
        return companies.values().stream().filter(company ->
                company.getCompanyName().text().value().equalsIgnoreCase(companyName.text().value()))
                .findFirst();
    }

    @Nonnull
    @Override
    public Company update(@Nonnull Company oldCompany, @Nonnull Company newCompany) {
        companies.replace(oldCompany.getId(), newCompany);
        return newCompany;
    }
}
