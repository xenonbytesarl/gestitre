package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
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
import cm.xenonbyte.gestitre.domain.company.vo.IsinCode;
import cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber;
import cm.xenonbyte.gestitre.domain.company.vo.TaxNumber;
import cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@DomainService
public final class CompanyDomainService implements CompanyService {

    private static final Logger LOGGER = Logger.getLogger(CompanyDomainService.class.getName());

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
        companyRepository.create(company);
        LOGGER.info("Company is created with id " + company.getId().getValue());
        //TODO fire create event to audit manager
        return new CompanyCreatedEvent(company, ZonedDateTime.now());
    }

    @Nonnull
    @Override
    public Company findCompanyById(@Nonnull CompanyId companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new CompanyNotFoundException(new String[]{companyId.getValue().toString()})
        );
        LOGGER.info("Company found with id " + companyId.getValue());
        return company;
    }

    @Override
    public PageInfo<Company> findCompanies(
            @Nonnull PageInfoPage pageInfoPage,
            @Nonnull PageInfoSize pageInfoSize,
            @Nonnull PageInfoField pageInfoField,
            @Nonnull PageInfoDirection pageInfoDirection
    ) {
        validatePageParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        PageInfo<Company> companyPageInfo = companyRepository.findAll(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        LOGGER.info("Found " + companyPageInfo.getTotalElements() + " companies");
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
        validatePageParameters(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
        Assert.field("Keyword", keyword).notNull();
        PageInfo<Company> companyPageInfo = companyRepository.search(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
        LOGGER.info("Found " + companyPageInfo.getTotalElements() + " companies for keyword " + keyword.text().value());
        return companyPageInfo;
    }

    @Nonnull
    @Override
    public CompanyUpdatedEvent updateCompany(@Nonnull CompanyId companyId, @Nonnull Company newCompany) {
        newCompany.validateMandatoryFields();
        findCompanyById(companyId);
        validateCompany(newCompany);
        newCompany = companyRepository.update(companyId, newCompany);
        LOGGER.info("Company updated with id " + newCompany.getId().getValue());
        //TODO fire update company event to audit manager
        return new CompanyUpdatedEvent(newCompany, ZonedDateTime.now());
    }

    private void validateCompany(Company company) {
        validateCompanyName(company.getId(), company.getCompanyName());
        validateCompanyEmail(company.getId(), company.getContact().email());
        validateCompanyPhone(company.getId(), company.getContact().phone());
        validateRegistrationNumber(company.getId(), company.getRegistrationNumber());
        validateTaxNumber(company.getId(), company.getTaxNumber());
        validateIsinCode(company.getId(), company.getIsinCode());
        validateWebSiteUrl(company.getId(), company.getWebSiteUrl());

        if(company.getCertificateTemplateId() != null) {
            validateCertificateTemplate(company.getCertificateTemplateId());
        }
    }

    private void validateRegistrationNumber(CompanyId companyId, RegistrationNumber registrationNumber) {
        if(registrationNumber != null) {
            if(companyId == null && companyRepository.existByRegistrationNumber(registrationNumber)) {
                throw new CompanyRegistrationNumberConflictException(new String[] {registrationNumber.text().value()});
            }

            Optional<Company> oldCompany = companyRepository.findByRegistrationNumber(registrationNumber);
            if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
                throw new CompanyRegistrationNumberConflictException(new String[] {registrationNumber.text().value()});
            }
        }
    }

    private void validateTaxNumber(CompanyId companyId, TaxNumber taxNumber) {
        if(taxNumber != null) {
            if(companyId == null && companyRepository.existByTaxNumber(taxNumber)) {
                throw new CompanyTaxNumberConflictException(new String[] {taxNumber.text().value()});
            }

            Optional<Company> oldCompany = companyRepository.findByTaxNumber(taxNumber);
            if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
                throw new CompanyTaxNumberConflictException(new String[] {taxNumber.text().value()});
            }
        }
    }

    private void validateIsinCode(CompanyId companyId, IsinCode isinCode) {
        if(isinCode != null) {
            if(companyId == null && companyRepository.existByIsinCode(isinCode)) {
                throw new CompanyIsinCodeConflictException(new String[] {isinCode.text().value()});
            }

            Optional<Company> oldCompany = companyRepository.findByIsinCode(isinCode);
            if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
                throw new CompanyIsinCodeConflictException(new String[] {isinCode.text().value()});
            }
        }
    }

    private void validateWebSiteUrl(CompanyId companyId, WebSiteUrl webSiteUrl) {
        if(webSiteUrl != null) {
            if(companyId == null && companyRepository.existByWebSiteUrl(webSiteUrl)) {
                throw new CompanyWebSiteUrlConflictException(new String[] {webSiteUrl.text().value()});
            }

            Optional<Company> oldCompany = companyRepository.findByWebSiteUrl(webSiteUrl);
            if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
                throw new CompanyWebSiteUrlConflictException(new String[] {webSiteUrl.text().value()});
            }
        }
    }

    private void validateCompanyPhone(CompanyId companyId, Phone phone) {
       if(phone != null) {
           if(companyId == null && companyRepository.existsByPhone(phone)) {
               throw new CompanyPhoneConflictException(new String[] {phone.text().value()});
           }

           Optional<Company> oldCompany = companyRepository.findByPhone(phone);
           if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
               throw new CompanyPhoneConflictException(new String[] {phone.text().value()});
           }
       }
    }

    private void validateCompanyEmail(CompanyId companyId, Email email) {
        if(companyId == null && companyRepository.existsByEmail(email)) {
            throw new CompanyEmailConflictException(new String[] {email.text().value()});
        }

        Optional<Company> oldCompany = companyRepository.findByEmail(email);
        if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
            throw new CompanyEmailConflictException(new String[] {email.text().value()});
        }
    }

    private void validateCompanyName(CompanyId companyId, CompanyName companyName) {
        if(companyId == null && companyRepository.existsByCompanyName(companyName)) {
            throw new CompanyNameConflictException(new String[] {companyName.text().value()});
        }

        Optional<Company> oldCompany = companyRepository.findByCompanyName(companyName);
        if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
            throw new CompanyNameConflictException(new String[] {companyName.text().value()});
        }
    }

    private void validateCertificateTemplate(CertificateTemplateId certificateTemplateId) {
        if(Boolean.FALSE.equals(certificateTemplateRepository.existsById(certificateTemplateId))) {
            throw new CertificateTemplateNotFoundException(new String[] {certificateTemplateId.getValue().toString()});
        }
    }

    private static void validatePageParameters(
            PageInfoPage pageInfoPage,
            PageInfoSize pageInfoSize,
            PageInfoField pageInfoField,
            PageInfoDirection pageInfoDirection) {
        Assert.field("Page", pageInfoPage).notNull();
        Assert.field("Size", pageInfoSize).notNull();
        Assert.field("Field", pageInfoField).notNull();
        Assert.field("Direction", pageInfoDirection).notNull();
    }
}
