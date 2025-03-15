package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.infrastructure.common.Tenantable;
import cm.xenonbyte.gestitre.infrastructure.company.CompanyJpa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_stock_move")
public class StockMoveJpa extends Tenantable {

    @Column(name = "c_reference", unique = true, nullable = false, length = 32)
    private String reference;

    @Column(name = "c_company_name", nullable = false, length = 64)
    private String companyName;

    @Column(name = "c_isin_code", nullable = false, length = 32)
    private String isinCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_nature", nullable = false, length = 32)
    private StockNatureJpa natureJpa;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", nullable = false, length = 32)
    private StockMoveTypeJpa typeJpa;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_state", nullable = false, length = 32)
    private StockMoveStateJpa stateJpa;

    @Column(name = "c_quantity_credit", nullable = false)
    private BigInteger quantityCredit;

    @Column(name = "c_quantity_debit", nullable = false)
    private BigInteger quantityDebit;

    @Column(name = "c_filename", nullable = false, length = 512)
    private String filename;

    @Column(name = "c_observation", nullable = false, length = 1024)
    private String observation;

    @Column(name = "c_created_date", nullable = false)
    private ZonedDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "c_company_id", nullable = false)
    private CompanyJpa companyJpa;

    @OneToMany(mappedBy = "stockMoveJpa")
    private List<StockMoveLineJpa> moveLinesJpa;

    @PrePersist
    public void convertToUTC() {
        this.createdDate = this.createdDate.withZoneSameInstant(ZoneOffset.UTC);
    }

}
