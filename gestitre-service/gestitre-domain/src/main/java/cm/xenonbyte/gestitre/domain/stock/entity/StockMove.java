package cm.xenonbyte.gestitre.domain.stock.entity;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.entity.AggregateRoot;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.validation.InvalidFieldBadException;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.common.vo.Filename;
import cm.xenonbyte.gestitre.domain.common.vo.Quantity;
import cm.xenonbyte.gestitre.domain.common.vo.Reference;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.company.vo.IsinCode;
import cm.xenonbyte.gestitre.domain.stock.vo.Observation;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveId;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveState;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveType;
import cm.xenonbyte.gestitre.domain.stock.vo.StockNature;
import jakarta.annotation.Nonnull;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static cm.xenonbyte.gestitre.domain.stock.vo.StockMoveState.DRAFT;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public final class StockMove extends AggregateRoot<StockMoveId> {
    private static final String STOCK_MOVE_QUANTITY_DIFFERENT = "StockMove.1";
    private final CompanyId companyId;
    private final CompanyName companyName;
    private final IsinCode isinCode;
    private final StockNature nature;
    private final StockMoveType type;
    private final Quantity quantityCredit;
    private final Quantity quantityDebit;
    private final Filename filename;
    private TenantId tenantId;
    private ZonedDateTime createdDate;
    private StockMoveState state;
    private Observation observation;
    private Reference reference;
    private List<StockMoveLine> moveLines;

    public StockMove(
            @Nonnull CompanyId companyId, CompanyName companyName,
            @Nonnull IsinCode isinCode,
            @Nonnull StockNature nature,
            @Nonnull StockMoveType type,
            @Nonnull Quantity quantityCredit,
            @Nonnull Quantity quantityDebit,
            @Nonnull Filename filename
    ) {
        this.companyId = Objects.requireNonNull(companyId);
        this.companyName = Objects.requireNonNull(companyName);
        this.isinCode = Objects.requireNonNull(isinCode);
        this.nature = Objects.requireNonNull(nature);
        this.type = Objects.requireNonNull(type);
        this.quantityCredit = Objects.requireNonNull(quantityCredit);
        this.quantityDebit = Objects.requireNonNull(quantityDebit);
        this.filename = Objects.requireNonNull(filename);
    }

    private StockMove(Builder builder) {
        setId(builder.id);
        companyId = builder.companyId;
        companyName = builder.companyName;
        isinCode = builder.isinCode;
        nature = builder.nature;
        type = builder.type;
        quantityCredit = builder.quantityCredit;
        quantityDebit = builder.quantityDebit;
        filename = builder.filename;
        tenantId = builder.tenantId;
        createdDate = builder.createdDate;
        state = builder.state;
        observation = builder.observation;
        reference = builder.reference;
        moveLines = builder.moveLines;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateMandatoryFields() {
        Assert.field("Company", companyId)
                .notNull(companyId);

        Assert.field("Company Name", companyName)
                .notNull(companyName);

        Assert.field("ISIN Code", isinCode)
                .notNull(isinCode);

        Assert.field("Stock nature", nature)
                .notNull(nature);

        Assert.field("Stock move type", type)
                .notNull(type);

        Assert.field("Quantity credit", quantityCredit)
                .notNull(quantityCredit);

        Assert.field("Quantity debit", quantityDebit)
                .notNull(quantityDebit);

        Assert.field("Filename", filename)
                .notNull(filename);

        Assert.field("Stock lines", moveLines)
                .notNull(moveLines)
                .minListLength(2, moveLines.size());

        moveLines.forEach(StockMoveLine::validateMandatoryFields);

        if(!quantityCredit.equals(quantityDebit)) {
            throw new InvalidFieldBadException(STOCK_MOVE_QUANTITY_DIFFERENT);
        }
    }

    public void initializeDefaults() {
        setId(new StockMoveId(UUID.randomUUID()));
        createdDate = ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId());
        state = DRAFT;
        AtomicInteger index = new AtomicInteger();
        moveLines = moveLines.stream()
                .map(moveLine -> moveLine.initializeDefaults(getId(), reference, index.incrementAndGet()))
                .toList();
    }

    public CompanyId getCompanyId() {
        return companyId;
    }

    public CompanyName getCompanyName() {
        return companyName;
    }

    public IsinCode getIsinCode() {
        return isinCode;
    }

    public StockNature getNature() {
        return nature;
    }

    public StockMoveType getType() {
        return type;
    }

    public Quantity getQuantityCredit() {
        return quantityCredit;
    }

    public Quantity getQuantityDebit() {
        return quantityDebit;
    }

    public Filename getFilename() {
        return filename;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public StockMoveState getState() {
        return state;
    }

    public Observation getObservation() {
        return observation;
    }

    public Reference getReference() {
        return reference;
    }

    public List<StockMoveLine> getMoveLines() {
        return moveLines;
    }


    public static final class Builder {
        private StockMoveId id;
        private CompanyId companyId;
        private CompanyName companyName;
        private IsinCode isinCode;
        private StockNature nature;
        private StockMoveType type;
        private Quantity quantityCredit;
        private Quantity quantityDebit;
        private Filename filename;
        private TenantId tenantId;
        private ZonedDateTime createdDate;
        private StockMoveState state;
        private Observation observation;
        private Reference reference;
        private List<StockMoveLine> moveLines;

        private Builder() {
        }

        public Builder id(StockMoveId val) {
            id = val;
            return this;
        }

        public Builder companyId(CompanyId val) {
            companyId = val;
            return this;
        }

        public Builder companyName(CompanyName val) {
            companyName = val;
            return this;
        }

        public Builder isinCode(IsinCode val) {
            isinCode = val;
            return this;
        }

        public Builder nature(StockNature val) {
            nature = val;
            return this;
        }

        public Builder type(StockMoveType val) {
            type = val;
            return this;
        }

        public Builder quantityCredit(Quantity val) {
            quantityCredit = val;
            return this;
        }

        public Builder quantityDebit(Quantity val) {
            quantityDebit = val;
            return this;
        }

        public Builder filename(Filename val) {
            filename = val;
            return this;
        }

        public Builder tenantId(TenantId val) {
            tenantId = val;
            return this;
        }

        public Builder createdDate(ZonedDateTime val) {
            createdDate = val;
            return this;
        }

        public Builder state(StockMoveState val) {
            state = val;
            return this;
        }

        public Builder observation(Observation val) {
            observation = val;
            return this;
        }

        public Builder reference(Reference val) {
            reference = val;
            return this;
        }

        public Builder moveLines(List<StockMoveLine> val) {
            moveLines = val;
            return this;
        }

        public StockMove build() {
            return new StockMove(this);
        }
    }
}
