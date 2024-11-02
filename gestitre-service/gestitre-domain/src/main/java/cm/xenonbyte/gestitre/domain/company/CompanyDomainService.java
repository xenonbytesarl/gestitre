package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyEmailConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNameConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyPhoneConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.CompanyRepository;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@DomainService
public final class CompanyDomainService implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CertificateTemplateRepository certificateTemplateRepository;

    public CompanyDomainService(
            @Nonnull CompanyRepository companyRepository,
            @Nonnull CertificateTemplateRepository certificateTemplateRepository) {
        this.companyRepository = Objects.requireNonNull(companyRepository);
        this.certificateTemplateRepository = Objects.requireNonNull(certificateTemplateRepository);
    }

    @Nonnull
    @Override
    public CompanyCreatedEvent createCompany(@Nonnull Company company) {
        company.validateMandatoryFields();
        validateCompany(company);
        company.initializeDefaultValues();
        companyRepository.save(company);
        return new CompanyCreatedEvent(company, ZonedDateTime.now());
    }

    @Nonnull
    @Override
    public Company findCompanyById(@Nonnull CompanyId companyId) {
        return companyRepository.findById(companyId).orElseThrow(
                () -> new CompanyNotFoundException(new String[] {companyId.getValue().toString()})
        );
    }

    @Override
    public PageInfo<Company> findCompanies(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection
    ) {
        Assert.field("Page", pageInfoPage).notNull();
        Assert.field("Size", pageInfoSize).notNull();
        Assert.field("Field", pageInfoField).notNull();
        Assert.field("Direction", pageInfoDirection).notNull();
        return companyRepository.findAll(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
    }

    @Override
    public PageInfo<Company> searchCompanies(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection,
            @Nonnull Keyword keyword
    ) {
        Assert.field("Page", pageInfoPage).notNull();
        Assert.field("Size", pageInfoSize).notNull();
        Assert.field("Field", pageInfoField).notNull();
        Assert.field("Direction", pageInfoDirection).notNull();
        Assert.field("Keyword", keyword).notNull();
        return companyRepository.search(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
    }

    @Nonnull
    @Override
    public CompanyUpdatedEvent updateCompany(@Nonnull CompanyId companyId, @Nonnull Company newCompany) {
        newCompany.validateMandatoryFields();
        findCompanyById(companyId);
        validateCompany(newCompany);
        newCompany = companyRepository.update(companyId, newCompany);
        return new CompanyUpdatedEvent(newCompany, ZonedDateTime.now());
    }

    private void validateCompany(Company company) {
        validateCompanyName(company.getId(), company.getCompanyName());
        validateCompanyEmail(company.getId(), company.getContact().email());
        validateCompanyPhone(company.getId(), company.getContact().phone());
        if(company.getCertificateTemplateId() != null) {
            validateCertificateTemplate(company.getCertificateTemplateId());
        }
    }

    private void validateCompanyPhone(CompanyId companyId, Phone phone) {
        if(companyId == null && companyRepository.existsByPhone(phone)) {
            throw new CompanyPhoneConflictException(new String[] {phone.text().value()});
        }

        Optional<Company> oldCompany = companyRepository.findCompanyByPhone(phone);
        if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
            throw new CompanyPhoneConflictException(new String[] {phone.text().value()});
        }
    }

    private void validateCompanyEmail(CompanyId companyId, Email email) {
        if(companyId == null && companyRepository.existsByEmail(email)) {
            throw new CompanyEmailConflictException(new String[] {email.text().value()});
        }

        Optional<Company> oldCompany = companyRepository.findCompanyByEmail(email);
        if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
            throw new CompanyEmailConflictException(new String[] {email.text().value()});
        }
    }

    private void validateCompanyName(CompanyId companyId, CompanyName companyName) {
        if(companyId == null && companyRepository.existsByCompanyName(companyName)) {
            throw new CompanyNameConflictException(new String[] {companyName.text().value()});
        }

        Optional<Company> oldCompany = companyRepository.findCompanyByCompanyName(companyName);
        if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
            throw new CompanyNameConflictException(new String[] {companyName.text().value()});
        }
    }

    private void validateCertificateTemplate(CertificateTemplateId certificateTemplateId) {
        if(Boolean.FALSE.equals(certificateTemplateRepository.existsById(certificateTemplateId))) {
            throw new CertificateTemplateNotFoundException(new String[] {certificateTemplateId.getValue().toString()});
        }
    }
}
