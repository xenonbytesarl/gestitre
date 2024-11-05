package cm.xenonbyte.gestitre.bootstrap.company;

import cm.xenonbyte.gestitre.application.common.in18.LocalizationUtil;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.CreateCertificateTemplateViewRequest;
import cm.xenonbyte.gestitre.application.company.certificatetemplate.dto.UpdateCertificateTemplateViewRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.UUID;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.CERTIFICATE_TEMPLATE_API_PATH;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.FR_LANGUAGE;
import static cm.xenonbyte.gestitre.application.common.exception.ApplicationUnwrapExceptionMapping.VALIDATION_ERROR_MESSAGE;
import static cm.xenonbyte.gestitre.application.company.certificatetemplate.CertificateTemplateResource.CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.application.company.certificatetemplate.CertificateTemplateResource.CERTIFICATE_TEMPLATE_CREATED_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.application.company.certificatetemplate.CertificateTemplateResource.CERTIFICATE_TEMPLATE_FIND_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.application.company.certificatetemplate.CertificateTemplateResource.CERTIFICATE_TEMPLATE_UPDATED_SUCCESSFULLY;
import static cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNameConflictException.CERTIFICATE_TEMPLATE_NAME_CONFLICT;
import static cm.xenonbyte.gestitre.domain.company.ports.CertificateTemplateNotFoundException.CERTIFICATE_TEMPLATE_NOT_FOUND;
import static io.restassured.RestAssured.given;
import static java.util.Locale.forLanguageTag;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
@QuarkusTest
@TestProfile(ITProfile.class)
final class CertificateTemplateIT {



    @Test
    void should_create_certificate_template() throws JsonProcessingException {
        //Given
        CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest =
                CreateCertificateTemplateViewRequest.builder().name("Certificate template IT").build();
        //Act + Then
        given()
            .contentType("application/json")
            .accept("application/json")
            .header("Accept-Language", FR_LANGUAGE)
            .body(new ObjectMapper().writeValueAsString(createCertificateTemplateViewRequest))
            .when()
            .post(CERTIFICATE_TEMPLATE_API_PATH)
            .then()
                .log().all()
                .statusCode(201)
                .body("code", equalTo(201))
                .body("status", equalTo("CREATED"))
                .body("success", equalTo(true))
                .body("data.content", notNullValue())
                .body("message", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_CREATED_SUCCESSFULLY, forLanguageTag(FR_LANGUAGE))));
    }

    @Test void should_fail_when_create_certificate_template_with_existing_name() throws JsonProcessingException {
        //Given
        String name = "Certificate Template 1";
        CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest = CreateCertificateTemplateViewRequest.builder()
                .name(name)
                .build();
        //Act + Then
        given()
            .contentType("application/json")
            .accept("application/json")
            .header("Accept-Language", FR_LANGUAGE)
            .body(new ObjectMapper().writeValueAsString(createCertificateTemplateViewRequest))
        .when()
            .post(CERTIFICATE_TEMPLATE_API_PATH)
        .then()
            .log().all()
            .statusCode(409)
            .body("code", equalTo(409))
            .body("status", equalTo("CONFLICT"))
            .body("success", equalTo(false))
            .body("reason", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_NAME_CONFLICT, forLanguageTag(FR_LANGUAGE), name)));
    }

    @Test void should_fail_when_create_certificate_template_with_empty_name() throws JsonProcessingException {
        //Given
        String name = "";
        CreateCertificateTemplateViewRequest createCertificateTemplateViewRequest = CreateCertificateTemplateViewRequest.builder()
                .name(name)
                .build();
        //Act + Then
        given()
            .contentType("application/json")
            .accept("application/json")
            .header("Accept-Language", FR_LANGUAGE)
            .body(new ObjectMapper().writeValueAsString(createCertificateTemplateViewRequest))
        .when()
            .post(CERTIFICATE_TEMPLATE_API_PATH)
        .then()
            .log().all()
            .statusCode(400)
            .body("code", equalTo(400))
            .body("status", equalTo("BAD_REQUEST"))
            .body("success", equalTo(false))
            .body("errors", notNullValue())
            .body("reason", equalTo(LocalizationUtil.getMessage(VALIDATION_ERROR_MESSAGE, forLanguageTag(FR_LANGUAGE))));
    }

    @Test
    void should_find_certificate_template_with_existing_id() {
        //Given
        UUID certificateTemplateId = UUID.fromString("0192e940-4c81-7a03-be3b-21d40ce60896");


        given()
            .header("Accept-Language", FR_LANGUAGE)
        .when()
            .get(CERTIFICATE_TEMPLATE_API_PATH + "/" + certificateTemplateId)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data.content", notNullValue())
            .body("message", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_FIND_SUCCESSFULLY, Locale.forLanguageTag(FR_LANGUAGE))));
    }

    @Test
    void should_not_find_certificate_template_with_non_existing_id() {
        //Given
        UUID certificateTemplateId = UUID.fromString("0192fe2c-cfbb-7b52-bcb6-f41b15a4fc70");


        given()
            .header("Accept-Language", FR_LANGUAGE)
        .when()
            .get(CERTIFICATE_TEMPLATE_API_PATH + "/" + certificateTemplateId)
        .then()
            .log().all()
            .statusCode(404)
            .body("code", equalTo(404))
            .body("status", equalTo("NOT_FOUND"))
            .body("success", equalTo(false))
            .body("reason", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_NOT_FOUND, Locale.forLanguageTag(FR_LANGUAGE),certificateTemplateId.toString())));
    }

    @Test
    void should_find_certificate_templates() {
        //Given
        int page = 0;
        int size = 2;
        String field = "name";
        String direction = "DESC";

        given()
            .header("Accept-Language", FR_LANGUAGE)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .param("field", field)
            .param("direction", direction)
        .when()
            .get(CERTIFICATE_TEMPLATE_API_PATH)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data.content", notNullValue())
            .body("message", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY, Locale.forLanguageTag(FR_LANGUAGE))));
    }

    @Test
    void should_not_empty_when_search_certificate_template_with_valid_keyword() {
        //Given
        int page = 0;
        int size = 2;
        String field = "name";
        String direction = "DESC";
        String keyword = "1";

        given()
            .header("Accept-Language", FR_LANGUAGE)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .param("field", field)
            .param("direction", direction)
            .param("keyword", keyword)
        .when()
            .get(CERTIFICATE_TEMPLATE_API_PATH + "/search")
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data.content", notNullValue())
            .body("data.content.elements", hasSize(greaterThan(0)))
            .body("message", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY, Locale.forLanguageTag(FR_LANGUAGE))));
    }

    @Test
    void should_empty_when_search_certificate_template_with_valid_keyword() {
        //Given
        int page = 0;
        int size = 2;
        String field = "name";
        String direction = "DESC";
        String keyword = "xxxxx";

        given()
            .header("Accept-Language", FR_LANGUAGE)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .param("field", field)
            .param("direction", direction)
            .param("keyword", keyword)
        .when()
            .get(CERTIFICATE_TEMPLATE_API_PATH + "/search")
        .then()
            .log().all()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("status", equalTo("OK"))
            .body("success", equalTo(true))
            .body("data.content", notNullValue())
            .body("data.content.elements", hasSize(equalTo(0)))
            .body("message", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATES_FIND_SUCCESSFULLY, Locale.forLanguageTag(FR_LANGUAGE))));
    }

    @Test
    void should_update_certificate_template() throws JsonProcessingException {
        //Given
        UUID certificateTemplateId = UUID.fromString("0192e940-cefa-74f3-a255-8466e9b08015");
        UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest = UpdateCertificateTemplateViewRequest.builder()
                .id(certificateTemplateId)
                .name("Certificate Template Updated")
                .active(true)
                .build();

        //Act + Then
        given()
                .contentType("application/json")
                .accept("application/json")
                .header("Accept-Language", FR_LANGUAGE)
                .body(new ObjectMapper().writeValueAsString(updateCertificateTemplateViewRequest))
            .when()
                .put(CERTIFICATE_TEMPLATE_API_PATH + "/" + certificateTemplateId)
            .then()
                .log().all()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("status", equalTo("OK"))
                .body("success", equalTo(true))
                .body("data.content", notNullValue())
                .body("message", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_UPDATED_SUCCESSFULLY, forLanguageTag(FR_LANGUAGE))));
    }

    @Test
    void should_fail_when_update_certificate_template_with_non_existing_id() throws JsonProcessingException {
        //Given
        UUID certificateTemplateId = UUID.fromString("0192fe52-b515-7a00-9380-277f74ba9aba");
        UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest = UpdateCertificateTemplateViewRequest.builder()
                .id(certificateTemplateId)
                .name("Certificate Template Updated")
                .active(true)
                .build();

        //Act + Then
        given()
            .contentType("application/json")
            .accept("application/json")
            .header("Accept-Language", FR_LANGUAGE)
            .body(new ObjectMapper().writeValueAsString(updateCertificateTemplateViewRequest))
        .when()
            .put(CERTIFICATE_TEMPLATE_API_PATH + "/" + certificateTemplateId)
        .then()
            .log().all()
            .statusCode(404)
            .body("code", equalTo(404))
            .body("status", equalTo("NOT_FOUND"))
            .body("success", equalTo(false))
            .body("reason", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_NOT_FOUND, forLanguageTag(FR_LANGUAGE), certificateTemplateId.toString())));
    }

    @Test
    void should_fail_when_update_certificate_template_with_conflict_name() throws JsonProcessingException {
        //Given
        UUID certificateTemplateId = UUID.fromString("0192e940-cefa-74f3-a255-8466e9b08015");
        String name = "Certificate Template 1";
        UpdateCertificateTemplateViewRequest updateCertificateTemplateViewRequest = UpdateCertificateTemplateViewRequest.builder()
                .id(certificateTemplateId)
                .name(name)
                .active(true)
                .build();

        //Act + Then
        given()
            .contentType("application/json")
            .accept("application/json")
            .header("Accept-Language", FR_LANGUAGE)
            .body(new ObjectMapper().writeValueAsString(updateCertificateTemplateViewRequest))
        .when()
            .put(CERTIFICATE_TEMPLATE_API_PATH + "/" + certificateTemplateId)
        .then()
            .log().all()
            .statusCode(409)
            .body("code", equalTo(409))
            .body("status", equalTo("CONFLICT"))
            .body("success", equalTo(false))
            .body("reason", equalTo(LocalizationUtil.getMessage(CERTIFICATE_TEMPLATE_NAME_CONFLICT, forLanguageTag(FR_LANGUAGE), name)));
    }
}
