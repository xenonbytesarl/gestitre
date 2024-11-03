package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.Filename;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.StorageLocation;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.addapter.inmemory.CertificateTemplateInMemoryRepository;
import cm.xenonbyte.gestitre.domain.company.addapter.inmemory.CompanyInMemoryRepository;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
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
import cm.xenonbyte.gestitre.domain.company.vo.address.Address;
import cm.xenonbyte.gestitre.domain.company.vo.address.City;
import cm.xenonbyte.gestitre.domain.company.vo.address.Country;
import cm.xenonbyte.gestitre.domain.company.vo.address.ZipCode;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Contact;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
import cm.xenonbyte.gestitre.domain.company.vo.Activity;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyManagerName;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.company.vo.LegalForm;
import cm.xenonbyte.gestitre.domain.company.vo.Licence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.company.entity.Company.DEFAULT_LOGO_PARENT_LOCATION;
import static cm.xenonbyte.gestitre.domain.company.entity.Company.DEFAULT_STAMP_PARENT_LOCATION;
import static cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNotFoundException.CERTIFICATE_TEMPLATE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyEmailConflictException.COMPANY_EMAIL_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyNameConflictException.COMPANY_NAME_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyNotFoundException.COMPANY_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyPhoneConflictException.COMPANY_PHONE_CONFLICT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
final class CompanyDomainTest {

    private CompanyService companyService;
    private CertificateTemplateId certificateTemplateId;
    private CompanyId companyId;

    @BeforeEach
    void setUp() {
        CompanyRepository companyRepository = new CompanyInMemoryRepository();
        CertificateTemplateRepository certificateTemplateRepository = new CertificateTemplateInMemoryRepository();
        companyService = new CompanyDomainService(companyRepository, certificateTemplateRepository);

        companyId = new CompanyId(UUID.fromString("0192e847-a74e-7802-9ca3-5c754010aff1"));
        Company company = Company.builder()
                .id(companyId)
                .companyName(CompanyName.of(Text.of("Company name 0")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager name")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("360")),
                                City.of(Text.of("City0")),
                                Country.of(Text.of("Country0"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 780")))
                                .name(Name.of(Text.of("Name0")))
                                .email(Email.of( Text.of("email0@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activity0")))
                .active(Active.with(true))
                .build();
        Company company1 = Company.builder()
                .id(new CompanyId(UUID.fromString("0192e858-8eae-7441-99f8-d940d6bdef2f")))
                .companyName(CompanyName.of(Text.of("Company name 1")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager name1")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("361")),
                                City.of(Text.of("City1")),
                                Country.of(Text.of("Country1"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 781")))
                                .name(Name.of(Text.of("Name1")))
                                .email(Email.of( Text.of("email1@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activity1")))
                .active(Active.with(true))
                .build();

        Company company2 = Company.builder()
                .id(new CompanyId(UUID.fromString("0192e85a-3853-7880-b305-eba0dc30891b")))
                .companyName(CompanyName.of(Text.of("Company name 2")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager name2")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("362")),
                                City.of(Text.of("City2")),
                                Country.of(Text.of("Country2"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 782")))
                                .name(Name.of(Text.of("Name2")))
                                .email(Email.of( Text.of("email2@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activity2")))
                .active(Active.with(true))
                .build();

        Company company3 = Company.builder()
                .id(new CompanyId(UUID.fromString("0192e85a-10ef-73b6-88cc-c42ec7494010")))
                .companyName(CompanyName.of(Text.of("Company name 3")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager name3")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("363")),
                                City.of(Text.of("City3")),
                                Country.of(Text.of("Country3"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 783")))
                                .name(Name.of(Text.of("Name3")))
                                .email(Email.of( Text.of("email3@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activity2")))
                .active(Active.with(true))
                .build();

        companyRepository.create(company);
        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);

        certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e83e-35ea-7df0-a1ac-9189813e8f62"));

        certificateTemplateRepository.create(
                CertificateTemplate.builder()
                        .id(certificateTemplateId)
                        .name(Name.of(Text.of("Certificate template")))
                        .build()
        );
    }

    @Nested
    class CreateCompanyDomainTest {
        @Test
        void should_create_company() {
            //Given
            Company company = Company
                    .builder()
                    .companyName(CompanyName.of(Text.of("Company name x")))
                    .companyManagerName(CompanyManagerName.of(Text.of("Company manager name 1")))
                    .licence(Licence.MONTH_12)
                    .legalForm(LegalForm.SA)
                    .address(
                        Address.of(
                            ZipCode.of(Text.of("360")),
                            City.of(Text.of("Cityx")),
                            Country.of(Text.of("Countryx"))
                        )
                    )
                    .contact(
                        Contact.builder()
                            .phone(Phone.of(Text.of("698 254 784")))
                            .name(Name.of(Text.of("Namex")))
                            .email(Email.of( Text.of("emailx@email.test")))
                            .build()
                    )
                    .activity(Activity.of(Text.of("activityx")))
                    .certificateTemplateId(certificateTemplateId)
                    .build();
            //Act
            CompanyCreatedEvent actual = companyService.createCompany(company);
            //Then
            assertThat(actual).isNotNull();
            assertThat(actual.getCompany()).isNotNull();
            assertThat(actual.getCreatedAt()).isNotNull();
            assertThat(actual.getCompany().getId()).isNotNull();
            assertThat(actual.getCompany().getId().getValue()).isNotNull().isInstanceOf(UUID.class);
            assertThat(actual.getCompany().getLogoFilename()).isNotNull();
            assertThat(actual.getCompany().getStampFilename()).isNotNull();
            assertThat(actual.getCompany().getActive()).isNotNull();
            assertThat(actual.getCompany().getActive().value()).isTrue();
            assertThat(actual.getCompany()).isNotNull();
            assertThat(actual.getCreatedAt()).isNotNull();
        }
    }

    @Test
    void should_fail_when_create_company_with_duplicate_name() {
        //Given
        Company company = Company
                .builder()
                .companyName(CompanyName.of(Text.of("Company name 1")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager namey")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("360")),
                                City.of(Text.of("Cityy")),
                                Country.of(Text.of("Countryy"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 789")))
                                .name(Name.of(Text.of("Namey")))
                                .email(Email.of( Text.of("emaily@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activityy")))
                .build();
        //Act + Then
        assertThatThrownBy(() -> companyService.createCompany(company))
                .isInstanceOf(CompanyNameConflictException.class)
                .hasMessage(COMPANY_NAME_CONFLICT);
    }

    @Test
    void should_fail_when_create_company_with_duplicate_email() {
        //Given
        Company company = Company
                .builder()
                .companyName(CompanyName.of(Text.of("Company name y")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager namey")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("360")),
                                City.of(Text.of("Cityy")),
                                Country.of(Text.of("Countryy"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 783")))
                                .name(Name.of(Text.of("Namey")))
                                .email(Email.of( Text.of("email1@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activityy")))
                .build();
        //Act + Then
        assertThatThrownBy(() -> companyService.createCompany(company))
                .isInstanceOf(CompanyEmailConflictException.class)
                .hasMessage(COMPANY_EMAIL_CONFLICT);
    }

    @Test
    void should_fail_when_create_company_with_duplicate_phone() {
        //Given
        Company company = Company
                .builder()
                .companyName(CompanyName.of(Text.of("Company name y")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager namey")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("360")),
                                City.of(Text.of("Cityy")),
                                Country.of(Text.of("Countryy"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 783")))
                                .name(Name.of(Text.of("Namey")))
                                .email(Email.of( Text.of("emaily@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activityy")))
                .build();
        //Act + Then
        assertThatThrownBy(() -> companyService.createCompany(company))
                .isInstanceOf(CompanyPhoneConflictException.class)
                .hasMessage(COMPANY_PHONE_CONFLICT);
    }

    @Test
    void should_fail_when_create_company_with_non_existing_certificate_template() {
        //Given
        CertificateTemplateId certificateTemplateId1 =
                new CertificateTemplateId(UUID.fromString("0192e842-3f7d-7655-b535-320ad5eae268"));
        Company company = Company
                .builder()
                .companyName(CompanyName.of(Text.of("Company name y")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company manager namey")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .address(
                        Address.of(
                                ZipCode.of(Text.of("360")),
                                City.of(Text.of("Cityy")),
                                Country.of(Text.of("Countryy"))
                        )
                )
                .contact(
                        Contact.builder()
                                .phone(Phone.of(Text.of("698 254 785")))
                                .name(Name.of(Text.of("Namey")))
                                .email(Email.of( Text.of("emaily@email.test")))
                                .build()
                )
                .activity(Activity.of(Text.of("activityy")))
                .certificateTemplateId(certificateTemplateId1)
                .build();
        //Act + Then
        assertThatThrownBy(() -> companyService.createCompany(company))
            .isInstanceOf(CertificateTemplateNotFoundException.class)
            .hasMessage(CERTIFICATE_TEMPLATE_NOT_FOUND);
    }

    @Nested
    class FindCompanyByIdDomainTest {
        @Test
        void should_not_empty_when_find_company_with_existing_id() {
            //Given + Act
            Company actual = companyService.findCompanyById(companyId);
            //Then
            assertThat(actual).isNotNull();
            assertThat(actual.getId()).isEqualTo(companyId);
        }

        @Test
        void should_fail_when_find_company_with_not_existing_id() {
            //Given + Act + Then
            assertThatThrownBy(() -> companyService.findCompanyById(new CompanyId(UUID.fromString("0192e852-c7cc-7a60-8097-10b1a2546ff0"))))
                    .isInstanceOf(CompanyNotFoundException.class)
                    .hasMessage(COMPANY_NOT_FOUND);



        }
    }

    @Nested
    class FindAllCompaniesDomainTest {
        @Test
        void should_success_when_find_all_companies() {
            //Given
            PageInfoPage pageInfoPage = PageInfoPage.of(0);
            PageInfoSize pageInfoSize = PageInfoSize.of(2);
            PageInfoField pageInfoField = PageInfoField.of(Text.of("companyName"));
            PageInfoDirection pageInfoDirection = PageInfoDirection.ASC;
            //Act
            PageInfo<Company> actual = companyService.findCompanies(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection);
            //Then
            assertThat(actual).isNotNull();
            assertThat(actual.getTotalElements()).isPositive();
            assertThat(actual.getTotalPages()).isPositive();
            assertThat(actual.getElements()).isNotEmpty();
            assertThat(actual.getPageSize()).isPositive();
        }
    }

    @Nested
    class SearchCompanyDomainTest {
        @Test
        void should_not_empty_when_search_companies_with_existing_keyword() {
            //Given
            PageInfoPage pageInfoPage = PageInfoPage.of(0);
            PageInfoSize pageInfoSize = PageInfoSize.of(2);
            PageInfoField pageInfoField = PageInfoField.of(Text.of("companyName"));
            PageInfoDirection pageInfoDirection = PageInfoDirection.ASC;
            Keyword keyword = Keyword.of(Text.of("1"));
            //Act
            PageInfo<Company> actual = companyService.searchCompanies(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
            //Then
            assertThat(actual).isNotNull();
            assertThat(actual.getTotalElements()).isPositive();
            assertThat(actual.getTotalPages()).isPositive();
            assertThat(actual.getElements()).isNotEmpty();
            assertThat(actual.getPageSize()).isPositive();
        }

        @Test
        void should_empty_when_search_companies_with_existing_keyword() {
            //Given
            PageInfoPage pageInfoPage = PageInfoPage.of(0);
            PageInfoSize pageInfoSize = PageInfoSize.of(2);
            PageInfoField pageInfoField = PageInfoField.of(Text.of("companyName"));
            PageInfoDirection pageInfoDirection = PageInfoDirection.ASC;
            Keyword keyword = Keyword.of(Text.of("zzz"));
            //Act
            PageInfo<Company> actual = companyService.searchCompanies(pageInfoPage, pageInfoSize, pageInfoField, pageInfoDirection, keyword);
            //Then
            assertThat(actual).isNotNull();
            assertThat(actual.getTotalElements()).isZero();
            assertThat(actual.getTotalPages()).isZero();
            assertThat(actual.getElements()).isEmpty();
            assertThat(actual.getPageSize()).isZero();
        }
    }

    @Nested
    class UpdateCompanyDomainTest {
        @Test
        void should_update_company_by_id() {
            //Given
            CompanyManagerName companyManagerNameUpdate = CompanyManagerName.of(Text.of("Company manager name update"));
            Country country0Update = Country.of(Text.of("Country0Update"));
            City city0Update = City.of(Text.of("City0Update"));
            Company company = Company.builder()
                    .id(companyId)
                    .companyName(CompanyName.of(Text.of("Company name 0")))
                    .companyManagerName(companyManagerNameUpdate)
                    .licence(Licence.MONTH_12)
                    .legalForm(LegalForm.SA)
                    .address(
                            Address.of(
                                    ZipCode.of(Text.of("360")),
                                    city0Update,
                                    country0Update
                            )
                    )
                    .contact(
                            Contact.builder()
                                    .phone(Phone.of(Text.of("698 254 784")))
                                    .name(Name.of(Text.of("Name0Update")))
                                    .email(Email.of( Text.of("email0@email.test")))
                                    .build()
                    )
                    .activity(Activity.of(Text.of("activity0")))
                    .logoFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_LOGO_PARENT_LOCATION).path()))
                    .stampFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_STAMP_PARENT_LOCATION).path()))
                    .active(Active.with(true))
                    .build();
            //Act
            CompanyUpdatedEvent actual = companyService.updateCompany(companyId, company);
            //Then
            assertThat(actual).isNotNull();
            assertThat(actual.getCompany()).isNotNull();
            assertThat(actual.getCreatedAt()).isNotNull();
            assertThat(actual.getCompany().getId()).isNotNull();
            assertThat(actual.getCompany().getId().getValue()).isNotNull().isInstanceOf(UUID.class);
            assertThat(actual.getCompany().getLogoFilename()).isNotNull();
            assertThat(actual.getCompany().getStampFilename()).isNotNull();
            assertThat(actual.getCompany().getActive()).isNotNull();
            assertThat(actual.getCompany().getActive().value()).isTrue();
            assertThat(actual.getCompany()).isNotNull();
            assertThat(actual.getCreatedAt()).isNotNull();
            assertThat(actual.getCompany().getCompanyManagerName()).isEqualTo(companyManagerNameUpdate);
            assertThat(actual.getCompany().getAddress().country()).isEqualTo(country0Update);
            assertThat(actual.getCompany().getAddress().city()).isEqualTo(city0Update);
        }

        @Test
        void should_fail_when_update_company_by_with_existing_name() {
            //Given
            Company company = Company.builder()
                    .id(companyId)
                    .companyName(CompanyName.of(Text.of("Company name 1")))
                    .companyManagerName(CompanyManagerName.of(Text.of("Company manager name update")))
                    .licence(Licence.MONTH_12)
                    .legalForm(LegalForm.SA)
                    .address(
                            Address.of(
                                    ZipCode.of(Text.of("360")),
                                    City.of(Text.of("City0Update")),
                                    Country.of(Text.of("Country0Update"))
                            )
                    )
                    .contact(
                            Contact.builder()
                                    .phone(Phone.of(Text.of("698 254 784")))
                                    .name(Name.of(Text.of("Name0Update")))
                                    .email(Email.of( Text.of("email0@email.test")))
                                    .build()
                    )
                    .activity(Activity.of(Text.of("activity0")))
                    .logoFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_LOGO_PARENT_LOCATION).path()))
                    .stampFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_STAMP_PARENT_LOCATION).path()))
                    .active(Active.with(true))
                    .build();
            //Act
            assertThatThrownBy(() -> companyService.updateCompany(companyId, company))
                    .isInstanceOf(CompanyNameConflictException.class)
                    .hasMessage(COMPANY_NAME_CONFLICT);
        }

        @Test
        void should_fail_when_update_company_by_with_existing_email() {
            //Given
            Company company = Company.builder()
                    .id(companyId)
                    .companyName(CompanyName.of(Text.of("Company name 0")))
                    .companyManagerName(CompanyManagerName.of(Text.of("Company manager name update")))
                    .licence(Licence.MONTH_12)
                    .legalForm(LegalForm.SA)
                    .address(
                            Address.of(
                                    ZipCode.of(Text.of("360")),
                                    City.of(Text.of("City0Update")),
                                    Country.of(Text.of("Country0Update"))
                            )
                    )
                    .contact(
                            Contact.builder()
                                    .phone(Phone.of(Text.of("698 254 784")))
                                    .name(Name.of(Text.of("Name0Update")))
                                    .email(Email.of( Text.of("email1@email.test")))
                                    .build()
                    )
                    .activity(Activity.of(Text.of("activity0")))
                    .logoFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_LOGO_PARENT_LOCATION).path()))
                    .stampFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_STAMP_PARENT_LOCATION).path()))
                    .active(Active.with(true))
                    .build();
            //Act
            assertThatThrownBy(() -> companyService.updateCompany(companyId, company))
                    .isInstanceOf(CompanyEmailConflictException.class)
                    .hasMessage(COMPANY_EMAIL_CONFLICT);

        }

        @Test
        void should_fail_when_update_company_by_with_existing_phone() {
            //Given
            Company company = Company.builder()
                    .id(companyId)
                    .companyName(CompanyName.of(Text.of("Company name 0")))
                    .companyManagerName(CompanyManagerName.of(Text.of("Company manager name update")))
                    .licence(Licence.MONTH_12)
                    .legalForm(LegalForm.SA)
                    .address(
                            Address.of(
                                    ZipCode.of(Text.of("360")),
                                    City.of(Text.of("City0Update")),
                                    Country.of(Text.of("Country0Update"))
                            )
                    )
                    .contact(
                            Contact.builder()
                                    .phone(Phone.of(Text.of("698 254 783")))
                                    .name(Name.of(Text.of("Name0Update")))
                                    .email(Email.of( Text.of("email0@email.test")))
                                    .build()
                    )
                    .activity(Activity.of(Text.of("activity0")))
                    .logoFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_LOGO_PARENT_LOCATION).path()))
                    .stampFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_STAMP_PARENT_LOCATION).path()))
                    .active(Active.with(true))
                    .build();
            //Act
            assertThatThrownBy(() -> companyService.updateCompany(companyId, company))
                    .isInstanceOf(CompanyPhoneConflictException.class)
                    .hasMessage(COMPANY_PHONE_CONFLICT);

        }

        @Test
        void should_fail_when_update_company_by_with_non_existing_certificate_template_id() {
            //Given
            Company company = Company.builder()
                    .id(companyId)
                    .companyName(CompanyName.of(Text.of("Company name 0")))
                    .companyManagerName(CompanyManagerName.of(Text.of("Company manager name update")))
                    .licence(Licence.MONTH_12)
                    .legalForm(LegalForm.SA)
                    .address(
                            Address.of(
                                    ZipCode.of(Text.of("360")),
                                    City.of(Text.of("City0Update")),
                                    Country.of(Text.of("Country0Update"))
                            )
                    )
                    .contact(
                            Contact.builder()
                                    .phone(Phone.of(Text.of("698 254 784")))
                                    .name(Name.of(Text.of("Name0Update")))
                                    .email(Email.of( Text.of("email0@email.test")))
                                    .build()
                    )
                    .activity(Activity.of(Text.of("activity0")))
                    .logoFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_LOGO_PARENT_LOCATION).path()))
                    .stampFilename(Filename.of(StorageLocation.computeStoragePtah(DEFAULT_STAMP_PARENT_LOCATION).path()))
                    .certificateTemplateId(new CertificateTemplateId(UUID.fromString("0192e8c4-8ef6-70d7-be93-9647d0a9b5c2")))
                    .active(Active.with(true))
                    .build();
            //Act
            assertThatThrownBy(() -> companyService.updateCompany(companyId, company))
                    .isInstanceOf(CertificateTemplateNotFoundException.class)
                    .hasMessage(CERTIFICATE_TEMPLATE_NOT_FOUND);

        }
    }
}
