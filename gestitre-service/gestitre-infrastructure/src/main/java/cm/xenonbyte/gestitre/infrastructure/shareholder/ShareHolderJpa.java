package cm.xenonbyte.gestitre.infrastructure.shareholder;

import cm.xenonbyte.gestitre.infrastructure.common.Tenantable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_shareholder")
public class ShareHolderJpa extends Tenantable {
    @Column(name = "c_name", nullable = false, length = 128)
    private String name;

    @Column(name = "c_administrator", length = 128)
    private String administrator;

    @Column(name = "c_account_number", nullable = false, unique = true, length = 64)
    private String accountNumber;

    @Column(name = "c_bank_account_number", unique = true, length = 64)
    private String bankAccountNumber;

    @Column(name = "c_email", unique = true, length = 128)
    private String email;

    @Column(name = "c_phone", unique = true, length = 32)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_account_type", nullable = false, length = 32)
    private AccountTypeJpa accountTypeJpa;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_shareholder_type", length = 32)
    private ShareHolderTypeJpa shareHolderTypeJpa;

    @Column(name = "c_city", length = 64)
    private String city;

    @Column(name = "c_zip_code", length = 64)
    private String zipCode;

    @Column(name = "c_tax_residence", nullable = false, length = 64)
    private String taxResidence;

    @Column(name = "c_initial_balance", nullable = false)
    private BigDecimal initialBalance;

    @Column(name = "c_created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Embedded
    private RepresentativeJpa representativeJpa;

    @Embedded
    private SuccessorJpa successorJpa;

    @Column(name = "c_active", nullable = false)
    private Boolean active;
}
