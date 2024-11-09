package cm.xenonbyte.gestitre.infrastructure.company;

import cm.xenonbyte.gestitre.infrastructure.common.Audit;
import cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate.CertificateTemplateJpa;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_company")
public class CompanyJpa extends Audit {

    @Column(name = "c_company_name", nullable = false, unique = true, length = 64)
    private String companyName;

    @Column(name = "c_company_manager_name", nullable = false, length = 64)
    private String companyManagerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_licence", nullable = false, length = 64)
    private LicenceJpa licenceJpa;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_legal_form", nullable = false, length = 64)
    private LegalFormJpa legalFormJpa;

    @Embedded
    private AddressJpa addressJpa;

    @Embedded
    private ContactJpa contactJpa;

    @Column(name = "c_activity", length = 128)
    private String activity;

    @Column(name = "c_registration_number",  length = 32)
    private String registrationNumber;

    @Column(name = "c_website_url",  length = 64)
    private String webSiteUrl;

    @Column(name = "c_isin_code",  length = 32)
    private String isinCode;

    @Column(name = "c_tax_number",  length = 32)
    private String taxNumber;

    @Column(name = "c_logo_filename", length = 512)
    private String logoFilename;

    @Column(name = "c_stamp_filename", length = 512)
    private String stampFilename;

    @Column(name = "c_gross_dividend_stock_unit")
    private BigDecimal grossDividendStockUnit;

    @Column(name = "c_nominal_value")
    private BigDecimal nominalValue;

    @Column(name = "c_stock_quantity")
    private Long stockQuantity;

    @Column(name = "c_active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "c_certificate_template_id")
    private CertificateTemplateJpa certificateTemplateJpa;
}
