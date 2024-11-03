package cm.xenonbyte.gestitre.infrastructure;

import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.entity.CertificateTemplate;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate.CertificateTemplateJpaMapper;
import cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate.CertificateTemplateJpaRepository;
import cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate.CertificateTemplateJpaRepositoryAdapter;
import io.quarkus.test.junit.QuarkusTest;
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
 * @since 01/11/2024
 */
@QuarkusTest
final class CertificateTemplateJpaRepositoryIT {

    @Inject
    CertificateTemplateJpaRepository certificateTemplateJpaRepository;

    @Inject
    CertificateTemplateJpaMapper certificateTemplateJpaMapper;

    private CertificateTemplateJpaRepositoryAdapter certificateTemplateJpaRepositoryAdapter;

    @BeforeEach
    void setUp() {
        certificateTemplateJpaRepositoryAdapter = new CertificateTemplateJpaRepositoryAdapter(
                certificateTemplateJpaRepository, certificateTemplateJpaMapper
        );
    }

    @Test
    @Transactional
    void should_create_certificate_template() {
        //Given
        CertificateTemplateId certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e955-ce7b-7166-8e4c-0d847025feac"));
        CertificateTemplate certificateTemplate = CertificateTemplate.builder()
                .id(certificateTemplateId)
                .name(Name.of(Text.of("Certificate Template")))
                .active(Active.with(true))
                .build();
        //Act
        CertificateTemplate actual = certificateTemplateJpaRepositoryAdapter.create(certificateTemplate);

        Optional<CertificateTemplate> dbCertificateTemplate = certificateTemplateJpaRepositoryAdapter.findById(certificateTemplateId);


        //Then
        assertThat(actual).isEqualTo(dbCertificateTemplate.get());
    }

    @Test
    void should_true_when_check_certificate_with_existing_name() {
        //Given
        Name name = Name.of(Text.of("Certificate Template 1"));
        //Act
        Boolean actual = certificateTemplateJpaRepositoryAdapter.existsByName(name);
        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void should_false_when_check_certificate_with_non_existing_name() {
        //Given
        Name name = Name.of(Text.of("xxxxx"));
        //Act
        Boolean actual = certificateTemplateJpaRepositoryAdapter.existsByName(name);
        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void should_true_when_check_certificate_with_existing_id() {
        //Given
        CertificateTemplateId certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e940-cefa-74f3-a255-8466e9b08015"));
        //Act
        Boolean actual = certificateTemplateJpaRepositoryAdapter.existsById(certificateTemplateId);
        //Then
        assertThat(actual).isTrue();

    }

    @Test
    void should_false_when_check_certificate_with_non_existing_id() {
        //Given
        CertificateTemplateId certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e980-3b1a-7df3-9024-d8cf672b1404"));
        //Act
        Boolean actual = certificateTemplateJpaRepositoryAdapter.existsById(certificateTemplateId);
        //Then
        assertThat(actual).isFalse();

    }

    @Test
    void should_not_empty_when_find_certificate_with_existing_id() {
        //Given
        CertificateTemplateId certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e940-cefa-74f3-a255-8466e9b08015"));
        //Act
        Optional<CertificateTemplate> actual = certificateTemplateJpaRepositoryAdapter.findById(certificateTemplateId);
        //Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void should_empty_when_find_certificate_with_non_existing_id() {
        //Given
        CertificateTemplateId certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e980-3b1a-7df3-9024-d8cf672b1404"));
        //Act
        Optional<CertificateTemplate> actual = certificateTemplateJpaRepositoryAdapter.findById(certificateTemplateId);
        //Then
        assertThat(actual).isEmpty();

    }

    @Test
    @Transactional
    void should_update_certificate_template() {
        //Given
        CertificateTemplateId certificateTemplateId = new CertificateTemplateId(UUID.fromString("0192e940-cefa-74f3-a255-8466e9b08015"));
        CertificateTemplate newCertificateTemplate = CertificateTemplate.builder()
                .id(certificateTemplateId)
                .name(Name.of(Text.of("Certificate Template Updated")))
                .active(Active.with(true))
                .build();

        //Act
        CertificateTemplate actual = certificateTemplateJpaRepositoryAdapter.update(certificateTemplateId, newCertificateTemplate);

        Optional<CertificateTemplate> updatedCertificateTemplate = certificateTemplateJpaRepositoryAdapter.findById(certificateTemplateId);

        //Then
        assertThat(actual).isEqualTo(updatedCertificateTemplate.get());
        assertThat(actual.getName()).isEqualTo(updatedCertificateTemplate.get().getName());
    }

    @Test
    void should_find_all_certificate_template() {
        //Given + Act
        PageInfo<CertificateTemplate> actual = certificateTemplateJpaRepositoryAdapter.findAll(
                PageInfoPage.of(0), PageInfoSize.of(2), PageInfoField.of(Text.of("name")), PageInfoDirection.ASC);
        //Then
        assertThat(actual).isNotNull();
        assertThat(actual.getTotalElements()).isPositive();
        assertThat(actual.getTotalPages()).isPositive();
        assertThat(actual.getElements()).isNotEmpty();
    }

    @Test
    void should_search_certificate_template_with_existing_keyword() {
        //Given + Act
        PageInfo<CertificateTemplate> actual =
                certificateTemplateJpaRepositoryAdapter.search(
                        PageInfoPage.of(0),
                        PageInfoSize.of(2),
                        PageInfoField.of(Text.of("name")),
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
