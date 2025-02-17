package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.event.CompanyCreatedEvent;
import cm.xenonbyte.gestitre.domain.company.event.CompanyUpdatedEvent;
import cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyEmailConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNameConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException;
import cm.xenonbyte.gestitre.domain.company.ports.CompanyPhoneConflictException;
import cm.xenonbyte.gestitre.domain.company.ports.primary.CompanyService;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.message.CompanyMessagePublisher;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CertificateTemplateRepository;
import cm.xenonbyte.gestitre.domain.company.ports.secondary.repository.CompanyRepository;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import cm.xenonbyte.gestitre.domain.company.vo.IsinCode;
import cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber;
import cm.xenonbyte.gestitre.domain.company.vo.TaxNumber;
import cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Fax;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static cm.xenonbyte.gestitre.domain.common.vo.PageInfo.validatePageParameters;
import static cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType.COMPANY_CREATED;
import static cm.xenonbyte.gestitre.domain.company.vo.CompanyEventType.COMPANY_UPDATED;

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
    private final CompanyMessagePublisher companyMessagePublisher;

    public CompanyDomainService(
            @Nonnull CompanyRepository companyRepository,
            @Nonnull CertificateTemplateRepository certificateTemplateRepository,
            @Nonnull CompanyMessagePublisher companyMessagePublisher) {
        this.companyRepository = Objects.requireNonNull(companyRepository);
        this.certificateTemplateRepository = Objects.requireNonNull(certificateTemplateRepository);
        this.companyMessagePublisher = Objects.requireNonNull(companyMessagePublisher);
    }

    @Nonnull
    @Override
    public CompanyCreatedEvent createCompany(@Nonnull Company company) {
        company.validateMandatoryFields();
        validateCompany(company);
        company.initializeDefaultValues();
        company = companyRepository.create(company);
        LOGGER.info("Company is created with id " + company.getId().getValue());
        CompanyCreatedEvent companyCreatedEvent = new CompanyCreatedEvent(company, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        companyMessagePublisher.publish(companyCreatedEvent, COMPANY_CREATED);
        return companyCreatedEvent;
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
        CompanyUpdatedEvent companyUpdatedEvent = new CompanyUpdatedEvent(newCompany, ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId()));
        companyMessagePublisher.publish(companyUpdatedEvent, COMPANY_UPDATED);
        return companyUpdatedEvent;
    }

    @Override
    public Boolean existsById(@Nonnull CompanyId companyId) {
        return companyRepository.existsById(companyId);
    }

    @Override
    public Company findCompanyByTenantBy(@Nonnull TenantId tenantId) {
        return companyRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new CompanyTenantIdNotFoundException(new String[]{tenantId.getValue().toString()}));
    }

    @Override
    public Company findCompanyByName(@Nonnull Name name) {
        return companyRepository.findByCompanyName(CompanyName.of(name.text())).orElseThrow(
                () -> new CompanyNameNotFoundException(new String[] {name.text().value()})
        );
    }


    private void validateCompany(Company company) {
        validateCompanyName(company.getId(), company.getCompanyName());
        validateCode(company.getId(), company.getCode());
        validateCompanyEmail(company.getId(), company.getContact().email());
        validateCompanyPhone(company.getId(), company.getContact().phone());
        validateCompanyFax(company.getId(), company.getContact().fax());
        validateRegistrationNumber(company.getId(), company.getRegistrationNumber());
        validateTaxNumber(company.getId(), company.getTaxNumber());
        validateIsinCode(company.getId(), company.getIsinCode());
        validateWebSiteUrl(company.getId(), company.getWebSiteUrl());

        if(company.getCertificateTemplateId() != null) {
            validateCertificateTemplate(company.getCertificateTemplateId());
        }
    }

    private void validateCode(CompanyId companyId, Code code) {
        if(code != null) {
            if(companyId == null && companyRepository.existsByCode(code)) {
                throw new CompanyCodeConflictException(new String[] {code.text().value()});
            }

            Optional<Company> oldCompany = companyRepository.findByCode(code);
            if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
                throw new CompanyCodeConflictException(new String[] {code.text().value()});
            }
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

    private void validateCompanyFax(CompanyId companyId, Fax fax) {
        if(fax != null) {
            if(companyId == null && companyRepository.existsByFax(fax)) {
                throw new CompanyFaxConflictException(new String[] {fax.text().value()});
            }

            Optional<Company> oldCompany = companyRepository.findByFax(fax);
            if(companyId != null && oldCompany.isPresent() && !oldCompany.get().getId().equals(companyId)) {
                throw new CompanyFaxConflictException(new String[] {fax.text().value()});
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
}
