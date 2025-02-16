package cm.xenonbyte.gestitre.infrastructure.stock;

import cm.xenonbyte.gestitre.infrastructure.common.Tenantable;
import cm.xenonbyte.gestitre.infrastructure.shareholder.ShareHolderJpa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "t_stock_move_line")
public final class StockMoveLineJpa extends Tenantable {

    @Column(name = "c_reference", unique = true, nullable = false, length = 32)
    private String reference;

    @Column(name = "c_account_number", nullable = false, length = 64)
    private String accountNumber;

    @Column(name = "c_name", nullable = false, length = 128)
    private String name;

    @Column(name = "c_quantity", nullable = false)
    private BigInteger quantity;

    @Column(name = "c_administrator", length = 128)
    private String administrator;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", nullable = false, length = 32)
    private StockMoveLineTypeJpa typeJpa;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_state", nullable = false, length = 32)
    private StockMoveLineStateJpa stateJpa;

    @Column(name = "c_city", length = 64)
    private String city;

    @Column(name = "c_created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name = "c_zip_code", length = 64)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "c_stock_move_id", nullable = false)
    private StockMoveJpa stockMoveJpa;

    @ManyToOne
    @JoinColumn(name = "c_shareholder_id", nullable = false)
    private ShareHolderJpa shareHolderJpa;

    @PrePersist
    public void convertToUTC() {
        this.createdDate = this.createdDate.withZoneSameInstant(ZoneOffset.UTC);
    }

}
