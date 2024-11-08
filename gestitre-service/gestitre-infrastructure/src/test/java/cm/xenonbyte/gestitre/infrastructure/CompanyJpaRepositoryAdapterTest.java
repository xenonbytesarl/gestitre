package cm.xenonbyte.gestitre.infrastructure;

import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Filename;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyManagerName;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.company.vo.LegalForm;
import cm.xenonbyte.gestitre.domain.company.vo.Licence;
import cm.xenonbyte.gestitre.domain.company.vo.address.Address;
import cm.xenonbyte.gestitre.domain.company.vo.address.City;
import cm.xenonbyte.gestitre.domain.company.vo.address.Country;
import cm.xenonbyte.gestitre.domain.company.vo.address.ZipCode;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Contact;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Phone;
import cm.xenonbyte.gestitre.infrastructure.company.CompanyJpaMapper;
import cm.xenonbyte.gestitre.infrastructure.company.CompanyJpaRepository;
import cm.xenonbyte.gestitre.infrastructure.company.CompanyJpaRepositoryAdapter;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@QuarkusTest
@TestProfile(ITProfile.class)
final class CompanyJpaRepositoryAdapterTest {

    @Inject
    CompanyJpaRepository companyJpaRepository;

    @Inject
    CompanyJpaMapper companyJpaMapper;

    CompanyJpaRepositoryAdapter companyJpaRepositoryAdapter;

    @BeforeEach
    void setUp() {
        companyJpaRepositoryAdapter = new CompanyJpaRepositoryAdapter(
                companyJpaRepository, companyJpaMapper
        );
    }

    @Test
    @Transactional
    void should_create_company() {
        //Given
        CompanyId companyId = new CompanyId(UUID.fromString("0192f295-3d0d-79ed-908d-eea72a20e840"));
        Company company = Company.builder()
                .id(companyId)
                .companyName(CompanyName.of(Text.of("Company Name 0")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company Manager Name 0")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .contact(
                    Contact.builder()
                        .email(Email.of(Text.of("email0@gmail.com")))
                        .name(Name.of(Text.of("Name 0")))
                        .build()
                )
                .address(
                    Address.builder()
                            .city(City.of(Text.of("City 0")))
                        .zipCode(ZipCode.of(Text.of("350")))
                        .country(Country.of(Text.of("Country 0")))
                        .build()
                )
                .logoFilename(Filename.of(Text.of(System.getProperty("user.home") + "/gestitre/logo/logo0.png")))
                .stampFilename(Filename.of(Text.of(System.getProperty("user.home") + "/gestitre/stamp/stamp0.png")))
                .active(Active.with(true))
                .build();

        //Act
        Company actual = companyJpaRepositoryAdapter.create(company);

        Optional<Company> createdCompany = companyJpaRepositoryAdapter.findById(companyId);

        //Then
        assertThat(actual).isEqualTo(createdCompany.get());
    }

    @Test
    void should_true_when_exists_company_by_company_name() {
        //Given
        CompanyName companyNameX = CompanyName.of(Text.of("Company Name y"));
        //Act
        Boolean actual = companyJpaRepositoryAdapter.existsByCompanyName(companyNameX);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void should_false_when_exists_company_by_company_name() {
        //Given
        CompanyName companyNameW = CompanyName.of(Text.of("Company Name w"));
        //Act
        Boolean actual = companyJpaRepositoryAdapter.existsByCompanyName(companyNameW);
        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void should_true_when_exists_company_by_company_email() {
        //Given
        Email emailX = Email.of(Text.of("emailx@gmail.com"));
        //Act
        Boolean actual = companyJpaRepositoryAdapter.existsByEmail(emailX);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void should_false_when_exists_company_by_company_email() {
        //Given
        Email emailW = Email.of(Text.of("emailw@gmail.com"));
        //Act
        Boolean actual = companyJpaRepositoryAdapter.existsByEmail(emailW);
        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void should_true_when_exists_company_by_company_phone() {
        //Given
        Phone phoneX = Phone.of(Text.of("645 254 238"));
        //Act
        Boolean actual = companyJpaRepositoryAdapter.existsByPhone(phoneX);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void should_false_when_exists_company_by_company_phone() {
        //Given
        Phone phoneW = Phone.of(Text.of("645 214 259"));
        //Act
        Boolean actual = companyJpaRepositoryAdapter.existsByPhone(phoneW);
        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void should_not_empty_when_find_company_by_company_phone() {
        //Given
        Phone phoneX = Phone.of(Text.of("645 254 238"));
        //Act
        Optional<Company> actual = companyJpaRepositoryAdapter.findByPhone(phoneX);
        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void should_empty_when_find_company_by_company_phone() {
        //Given
        Phone phoneW = Phone.of(Text.of("645 214 259"));
        //Act
        Optional<Company> actual = companyJpaRepositoryAdapter.findByPhone(phoneW);
        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void should_not_empty_when_find_company_by_company_name() {
        //Given
        CompanyName companyNameX = CompanyName.of(Text.of("Company Name y"));
        //Act
        Optional<Company> actual = companyJpaRepositoryAdapter.findByCompanyName(companyNameX);
        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void should_empty_when_find_company_by_company_name() {
        //Given
        CompanyName companyNameW = CompanyName.of(Text.of("Company Name w"));
        //Act
        Optional<Company> actual = companyJpaRepositoryAdapter.findByCompanyName(companyNameW);
        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    @Transactional
    void should_update_company() {
        //Given
        CompanyId companyId = new CompanyId(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"));
        Company newCompany = Company.builder()
                .id(companyId)
                .companyName(CompanyName.of(Text.of("Company Name 2")))
                .companyManagerName(CompanyManagerName.of(Text.of("Company Manager Name 2")))
                .licence(Licence.MONTH_12)
                .legalForm(LegalForm.SA)
                .contact(
                        Contact.builder()
                                .email(Email.of(Text.of("email2@gmail.com")))
                                .name(Name.of(Text.of("Name 2")))
                                .build()
                )
                .address(
                        Address.builder()
                                .city(City.of(Text.of("City 2")))
                                .zipCode(ZipCode.of(Text.of("352")))
                                .country(Country.of(Text.of("Country 2")))
                                .build()
                )
                .logoFilename(Filename.of(Text.of(System.getProperty("user.home") + "/gestitre/logo/logo2.png")))
                .stampFilename(Filename.of(Text.of(System.getProperty("user.home") + "/gestitre/stamp/stamp2.png")))
                .active(Active.with(true))
                .build();

        //Act
        Company actual = companyJpaRepositoryAdapter.update(companyId, newCompany);

        Optional<Company> updatedCompany = companyJpaRepositoryAdapter.findById(companyId);

        //Then
        assertThat(actual).isEqualTo(updatedCompany.get());
    }

    @Test
    void should_find_all_certificate_template() {
        //Given + Act
        PageInfo<Company> actual = companyJpaRepositoryAdapter.findAll(
                PageInfoPage.of(0), PageInfoSize.of(2), PageInfoField.of(Text.of("companyName")), PageInfoDirection.ASC);
        //Then
        assertThat(actual).isNotNull();
        assertThat(actual.getTotalElements()).isPositive();
        assertThat(actual.getTotalPages()).isPositive();
        assertThat(actual.getElements()).isNotEmpty();
    }

    @Test
    void should_search_certificate_template_with_existing_keyword() {
        //Given + Act
        PageInfo<Company> actual =
                companyJpaRepositoryAdapter.search(
                        PageInfoPage.of(0),
                        PageInfoSize.of(2),
                        PageInfoField.of(Text.of("companyName")),
                        PageInfoDirection.ASC,
                        Keyword.of(Text.of(""))
                );
        //Then
        assertThat(actual).isNotNull();
        assertThat(actual.getTotalElements()).isPositive();
        assertThat(actual.getTotalPages()).isPositive();
        assertThat(actual.getElements()).isNotEmpty();
    }
}
