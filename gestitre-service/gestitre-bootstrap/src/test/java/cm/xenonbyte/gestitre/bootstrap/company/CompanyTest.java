package cm.xenonbyte.gestitre.bootstrap.company;

import cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil;
import cm.xenonbyte.gestitre.application.company.dto.AddressView;
import cm.xenonbyte.gestitre.application.company.dto.ContactView;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewRequest;
import cm.xenonbyte.gestitre.domain.admin.Permission;
import cm.xenonbyte.gestitre.domain.admin.Role;
import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.ports.secondary.TokenProvider;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountEnabled;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountExpired;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountLocked;
import cm.xenonbyte.gestitre.domain.admin.vo.CredentialExpired;
import cm.xenonbyte.gestitre.domain.admin.vo.FailedLoginAttempt;
import cm.xenonbyte.gestitre.domain.admin.vo.PermissionId;
import cm.xenonbyte.gestitre.domain.admin.vo.RoleId;
import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.admin.vo.UseMfa;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.COMPANY_API_PATH;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.FR_LANGUAGE;
import static cm.xenonbyte.gestitre.application.common.exception.ApplicationUnwrapExceptionMapping.VALIDATION_ERROR_MESSAGE;
import static cm.xenonbyte.gestitre.application.company.CompanyResource.COMPANY_CREATED_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.application.company.CompanyResource.COMPANY_FINDS_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.application.company.CompanyResource.COMPANY_FIND_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.application.company.CompanyResource.COMPANY_UPDATED_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.domain.company.CompanyIsinCodeConflictException.COMPANY_ISIN_CODE_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.CompanyRegistrationNumberConflictException.COMPANY_REGISTRATION_NUMBER_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.CompanyTaxNumberConflictException.COMPANY_TAX_NUMBER_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.CompanyWebSiteUrlConflictException.COMPANY_WEB_SITE_URL_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNotFoundException.CERTIFICATE_TEMPLATE_NOT_FOUND;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyEmailConflictException.COMPANY_EMAIL_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyNameConflictException.COMPANY_NAME_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.ports.CompanyPhoneConflictException.COMPANY_PHONE_CONFLICT;
import static io.restassured.RestAssured.given;
import static java.util.Locale.forLanguageTag;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
@QuarkusTest
@TestProfile(ITProfile.class)
class CompanyTest {

    @Inject
    TokenProvider tokenProvider;

    static Stream<Arguments> createCompanySuccessMethodSource() {
        return Stream.of(
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT0")
                                .code("CM20250214")
                                .companyManagerName("Company Manager Name IT0")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0")
                                                .email("emailit0@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0")
                                                .country("Country IT0")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        new MultiPartSpecBuilder("Logo content 2".getBytes())
                                .fileName("createCompanyLogo2.png")
                                .controlName("logo")
                                .mimeType("image/png")
                                .build(),

                        new MultiPartSpecBuilder("Stamp content 2".getBytes())
                                .fileName("createCompanyStamp2.png")
                                .controlName("stamp")
                                .mimeType("image/png")
                                .build()
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT1")
                                .code("CM20250215")
                                .companyManagerName("Company Manager Name IT1")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT1")
                                                .email("emailit1@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT1")
                                                .country("Country IT1")
                                                .zipCode("361")
                                                .build()
                                )
                                .build(),

                        new MultiPartSpecBuilder("Logo content 1".getBytes())
                                .fileName("createCompanyLogo1.png")
                                .controlName("logo")
                                .mimeType("image/png")
                                .build(),

                        new MultiPartSpecBuilder("Stamp content 1".getBytes())
                                .fileName("createCompanyStamp1.png")
                                .controlName("stamp")
                                .mimeType("image/png")
                                .build()
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT2")
                                .code("CM20250216")
                                .companyManagerName("Company Manager Name IT2")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .activity("Activity IT2")
                                .isinCode("JHDBKDBKVDK")
                                .taxNumber("DCBLCDNCDKN")
                                .webSiteUrl("http://hbdcbjdds.dcds")
                                .registrationNumber("djkcdjcdkjcdsjck")
                                .grossDividendStockUnit(new BigDecimal("5000"))
                                .nominalValue(new BigDecimal("5000"))
                                .certificateTemplateId(UUID.fromString("0192e940-4c81-7a03-be3b-21d40ce60896"))
                                .contact(
                                        ContactView.builder()
                                                .phone("215446461")
                                                .fax("215446461")
                                                .name("Name IT2")
                                                .email("emailit2@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .street("Street IT2")
                                                .city("City IT2")
                                                .country("Country IT2")
                                                .zipCode("361")
                                                .build()
                                )
                                .build(),

                        new MultiPartSpecBuilder("Logo content 2".getBytes())
                                .fileName("createCompanyLogo2.png")
                                .controlName("logo")
                                .mimeType("image/png")
                                .build(),

                        new MultiPartSpecBuilder("Stamp content 2".getBytes())
                                .fileName("createCompanyStamp2.png")
                                .controlName("stamp")
                                .mimeType("image/png")
                                .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("createCompanySuccessMethodSource")
    void should_create_company(
            CreateCompanyViewRequest createCompanyViewRequest,
            MultiPartSpecification logo,
            MultiPartSpecification stamp
    ) {
        //Act + Then
        given()
            .headers(getHeaders(getToken().accessToken().value()))
            .multiPart("createCompanyViewRequest", createCompanyViewRequest)
            .multiPart(logo)
            .multiPart(stamp)
        .when()
            .post(COMPANY_API_PATH)
        .then()
            .log().all()
            .statusCode(201)
            .body("code", equalTo(201))
            .body("status", equalTo("CREATED"))
            .body("success", equalTo(true))
            .body("data.content", notNullValue())
            .body("message", equalTo(LocalizationUtil.getMessage(COMPANY_CREATED_SUCCESSFULLY, forLanguageTag(FR_LANGUAGE))));
    }

    static Stream<Arguments> createCompanyFailMethodSource() {
        return Stream.of(
                Arguments.of(
                    CreateCompanyViewRequest.builder()
                        .companyName("Company Name IT0F")
                        .code("CM20250217")
                        .companyManagerName("Company Manager Name IT0F")
                        .licence("MONTH_12")
                        .legalForm("SA")
                            .certificateTemplateId(UUID.fromString("0192ff1a-bae0-74f2-820b-8dd50628e396"))
                        .contact(
                            ContactView.builder()
                                .name("Name IT0F")
                                .email("emailit0F@gmail.com")
                                .build()
                        )
                        .address(
                            AddressView.builder()
                                .city("City IT0F")
                                .country("Country IT0F")
                                .zipCode("360")
                                .build()
                        )
                        .build(),
                        404,
                        "NOT_FOUND",
                        CERTIFICATE_TEMPLATE_NOT_FOUND,
                        "0192ff1a-bae0-74f2-820b-8dd50628e396"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT0F")
                                .code("CM20250218")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .taxNumber("TN863534")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_TAX_NUMBER_CONFLICT,
                        "TN863534"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT0F")
                                .code("CM20250219")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .registrationNumber("RN73983773")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0@gmail.comF")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_REGISTRATION_NUMBER_CONFLICT,
                        "RN73983773"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT0F")
                                .code("CM20250220")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .isinCode("CIJKBCDJ98389")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_ISIN_CODE_CONFLICT,
                        "CIJKBCDJ98389"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT0F")
                                .code("CM20250221")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_WEB_SITE_URL_CONFLICT,
                        "https://company.com"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name IT0F")
                                .code("CM20250222")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .phone("645 254 239")
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_PHONE_CONFLICT,
                        "645 254 239"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name y")
                                .code("CM20250223")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .phone("645 254 239")
                                                .name("Name IT0F")
                                                .email("emailit0@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_NAME_CONFLICT,
                        "Company Name y"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name 0F")
                                .code("CM20250224")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .phone("645 254 239")
                                                .name("Name IT0F")
                                                .email("emailx@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_EMAIL_CONFLICT,
                        "emailx@gmail.com"
                ),
                Arguments.of(
                        CreateCompanyViewRequest.builder()
                                .companyName("Company Name 0F")
                                .code("CM20250225")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()

                                                .build()
                                )
                                .address(
                                        AddressView.builder()

                                                .build()
                                )
                                .build(),
                        400,
                        "BAD_REQUEST",
                        VALIDATION_ERROR_MESSAGE,
                        ""
                )
        );
    }

    @ParameterizedTest
    @MethodSource("createCompanyFailMethodSource")
    void should_create_company_fail(
        CreateCompanyViewRequest createCompanyViewRequest,
        int code,
        String status,
        String exceptionMessage,
        String exceptionArgs
    ) {
        //Act + Then
        given()
            .headers(getHeaders(getToken().accessToken().value()))
            .multiPart("createCompanyViewRequest", createCompanyViewRequest)
        .when()
            .post(COMPANY_API_PATH)
        .then()
            .log().all()
            .statusCode(code)
            .body("code", equalTo(code))
            .body("status", equalTo(status))
            .body("success", equalTo(false))
            .body("reason", equalTo(LocalizationUtil.getMessage(exceptionMessage, forLanguageTag(FR_LANGUAGE), exceptionArgs)));
    }

    @Test
    void should_find_company_by_existing_id() {
        //Given
        UUID companyId = UUID.fromString("019359ab-12ea-77d1-8a37-984affddd14f");
        //Act + Then
        given()
            .headers(getHeaders(getToken().accessToken().value()))
        .when()
            .get(COMPANY_API_PATH +  "/" + companyId)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data", notNullValue())
            .body("data.content", notNullValue())
            .body("message", equalTo(LocalizationUtil.getMessage(COMPANY_FIND_SUCCESSFULLY, forLanguageTag(FR_LANGUAGE), companyId)));
    }

    @Test
    void should_search_companies_with_valid_keyword() {
        //Given
        Integer page = 0;
        Integer size = 10;
        String field = "companyName";
        String direction = "ASC";
        String keyword = "emailx";

        //Act
        given()
            .headers(getHeaders(getToken().accessToken().value()))
            .queryParam("page", page)
            .queryParam("size", size)
            .queryParam("field", field)
            .queryParam("direction", direction)
            .queryParam("keyword", keyword)
        .when()
            .get(COMPANY_API_PATH)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data", notNullValue())
            .body("data.content", notNullValue())
            .body("data.content.elements", notNullValue())
            .body("message", equalTo(LocalizationUtil.getMessage(COMPANY_FINDS_SUCCESSFULLY, Locale.forLanguageTag(FR_LANGUAGE))));


    }

    static Stream<Arguments> updateCompanySuccessMethodSource() {
        return Stream.of(
                Arguments.of(
                       UUID.fromString("019359a9-6db3-79f8-98dd-4e55d2cccfff"),
                       UpdateCompanyViewRequest.builder()
                               .id(UUID.fromString("019359a9-6db3-79f8-98dd-4e55d2cccfff"))
                                .companyName("Company Name IT0Updated")
                                .companyManagerName("Company Manager Name IT0Updated")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0Updated")
                                                .email("emailit0Updated@gmail.com")
                                                .phone("21545874")
                                                .fax("87456931")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .street("Street IT0Updated")
                                                .city("City IT0Updated")
                                                .country("Country IT0Updated")
                                                .zipCode("360")
                                                .build()
                                )
                               .active(true)
                               .code("CM20250213")
                               .createdDate(ZonedDateTime.now())
                                .build(),
                        new MultiPartSpecBuilder("Logo content 2".getBytes())
                                .fileName("createCompanyLogo2.png")
                                .controlName("logo")
                                .mimeType("image/png")
                                .build(),

                        new MultiPartSpecBuilder("Stamp content 2".getBytes())
                                .fileName("createCompanyStamp2.png")
                                .controlName("stamp")
                                .mimeType("image/png")
                                .build()
                )

        );
    }

    @ParameterizedTest
    @MethodSource("updateCompanySuccessMethodSource")
    void should_update_company(
            UUID companyId,
            UpdateCompanyViewRequest updateCompanyViewRequest,
            MultiPartSpecification logo,
            MultiPartSpecification stamp
    ) {

        //Act + Then
        given()
            .headers(getHeaders(getToken().accessToken().value()))
            .multiPart("updateCompanyViewRequest", updateCompanyViewRequest)
            .multiPart(logo)
            .multiPart(stamp)
        .when()
            .put(COMPANY_API_PATH + "/" + companyId)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data.content", notNullValue())
            .body("message", equalTo(LocalizationUtil.getMessage(COMPANY_UPDATED_SUCCESSFULLY, forLanguageTag(FR_LANGUAGE))));
    }

    static Stream<Arguments> updateCompanyFailMethodSource() {
        return Stream.of(
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                    UpdateCompanyViewRequest.builder()
                        .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                        .companyName("Company Name IT0F")
                        .code("CM20250207")
                        .companyManagerName("Company Manager Name IT0F")
                        .licence("MONTH_12")
                        .legalForm("SA")
                        .certificateTemplateId(UUID.fromString("0192ff1a-bae0-74f2-820b-8dd50628e396"))
                        .contact(
                                ContactView.builder()
                                        .name("Name IT0F")
                                        .email("emailit0F@gmail.com")
                                        .build()
                        )
                        .address(
                                AddressView.builder()
                                        .city("City IT0F")
                                        .country("Country IT0F")
                                        .zipCode("360")
                                        .build()
                        )
                        .active(true)
                        .build(),
                        404,
                        "NOT_FOUND",
                        CERTIFICATE_TEMPLATE_NOT_FOUND,
                        "0192ff1a-bae0-74f2-820b-8dd50628e396"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                    UpdateCompanyViewRequest.builder()
                        .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                        .companyName("Company Name IT0F")
                        .code("CM20250208")
                        .companyManagerName("Company Manager Name IT0F")
                        .licence("MONTH_12")
                        .legalForm("SA")
                        .taxNumber("TN863534")
                        .contact(
                                ContactView.builder()
                                        .name("Name IT0F")
                                        .email("emailit0F@gmail.com")
                                        .build()
                        )
                        .address(
                                AddressView.builder()
                                        .city("City IT0F")
                                        .country("Country IT0F")
                                        .zipCode("360")
                                        .build()
                        )
                        .active(true)
                        .build(),
                        409,
                        "CONFLICT",
                        COMPANY_TAX_NUMBER_CONFLICT,
                        "TN863534"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                            UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name IT0F")
                                .code("CM20250209")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .registrationNumber("RN73983773")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0@gmail.comF")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .active(true)
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_REGISTRATION_NUMBER_CONFLICT,
                        "RN73983773"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                            UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name IT0F")
                                .code("CM20250210")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .isinCode("CIJKBCDJ98389")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .active(true)
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_ISIN_CODE_CONFLICT,
                        "CIJKBCDJ98389"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                            UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name IT0F")
                                .code("CM20250211")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .active(true)
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_WEB_SITE_URL_CONFLICT,
                        "https://company.com"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                             UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name IT0F")
                                 .code("CM20250212")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .phone("645 254 239")
                                                .name("Name IT0F")
                                                .email("emailit0F@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                 .active(true)
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_PHONE_CONFLICT,
                        "645 254 239"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                            UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name y")
                                .code("CM20250213")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .phone("645 254 239")
                                                .name("Name IT0F")
                                                .email("emailit0@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .active(true)
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_NAME_CONFLICT,
                        "Company Name y"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                            UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name 0F")
                                .code("CM20250214")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()
                                                .phone("645 254 239")
                                                .name("Name IT0F")
                                                .email("emaily@gmail.com")
                                                .build()
                                )
                                .address(
                                        AddressView.builder()
                                                .city("City IT0F")
                                                .country("Country IT0F")
                                                .zipCode("360")
                                                .build()
                                )
                                .active(true)
                                .build(),
                        409,
                        "CONFLICT",
                        COMPANY_EMAIL_CONFLICT,
                        "emaily@gmail.com"
                ),
                Arguments.of(
                        UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"),
                            UpdateCompanyViewRequest.builder()
                                .id(UUID.fromString("0192f2b7-c1d4-795e-8e9f-b382b4c61d01"))
                                .companyName("Company Name 0F")
                                .code("CM20250215")
                                .companyManagerName("Company Manager Name IT0F")
                                .licence("MONTH_12")
                                .legalForm("SA")
                                .webSiteUrl("https://company.com")
                                .contact(
                                        ContactView.builder()

                                                .build()
                                )
                                .address(
                                        AddressView.builder()

                                                .build()
                                )
                                .active(true)
                                .build(),
                        400,
                        "BAD_REQUEST",
                        VALIDATION_ERROR_MESSAGE,
                        ""
                )
        );
    }

    @ParameterizedTest
    @MethodSource("updateCompanyFailMethodSource")
    void should_update_company_fail(
            UUID companyId,
            UpdateCompanyViewRequest updateCompanyViewRequest,
            int code,
            String status,
            String exceptionMessage,
            String exceptionArgs
    ) {
        //Act + Then
        given()
            .headers(getHeaders(getToken().accessToken().value()))
            .multiPart("updateCompanyViewRequest", updateCompanyViewRequest)
        .when()
                .put(COMPANY_API_PATH + "/" + companyId)
        .then()
            .log().all()
            .statusCode(code)
            .body("code", equalTo(code))
            .body("status", equalTo(status))
            .body("success", equalTo(false))
            .body("reason", equalTo(LocalizationUtil.getMessage(exceptionMessage, forLanguageTag(FR_LANGUAGE), exceptionArgs)));
    }
    private Token getToken() {
        User user = User.builder()
                .id(new UserId(UUID.fromString("01937564-456e-7514-8c6c-1db19c1614d6")))
                .failedLoginAttempt(FailedLoginAttempt.of(0L))
                .accountEnabled(AccountEnabled.with(true))
                .accountExpired(AccountExpired.with(true))
                .accountLocked(AccountLocked.with(false))
                .credentialExpired(CredentialExpired.with(false))
                .companyId(new CompanyId(UUID.fromString("01937563-f905-7965-a014-da683621056c")))
                .tenantId(new TenantId(UUID.fromString("01937563-b2fc-79f9-bf40-d03bc39383f1")))
                .name(Name.of(Text.of("ROOT")))
                .email(Email.of(Text.of("ambiandji@gmail.com")))
                .useMfa(UseMfa.with(true))
                .timezone(Timezone.Africa_Douala)
                .roles(Set.of(
                        Role.builder()
                                .id(new RoleId(UUID.fromString("01935bdb-8ca1-768a-88d6-82335f785612")))
                                .name(Name.of(Text.of("System")))
                                .active(Active.with(true))
                                .permissions(Set.of(
                                        Permission.builder()
                                                .id(new PermissionId(UUID.fromString("01935bd9-2c9f-72a8-840b-ae72e943c715")))
                                                .name(Name.of(Text.of("create:company")))
                                                .build(),
                                        Permission.builder()
                                                .id(new PermissionId(UUID.fromString("01935bd9-2c9f-7951-bfc5-3c02921ac515")))
                                                .name(Name.of(Text.of("update:company")))
                                                .build(),
                                        Permission.builder()
                                                .id(new PermissionId(UUID.fromString("01935bd9-2c9f-7147-8f3e-97dbf32b3a08")))
                                                .name(Name.of(Text.of("read:company")))
                                                .build()
                                ))
                                .build()
                ))
                .build();
        return tokenProvider.generateToken(user);
    }

    private Map<String, String> getHeaders(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", FR_LANGUAGE);
        headers.put("Authorization", String.format("Bearer %s", token));
        headers.put("X-Gestitre-Tenant-Code", "ROOT");
        return headers;
    }
}
