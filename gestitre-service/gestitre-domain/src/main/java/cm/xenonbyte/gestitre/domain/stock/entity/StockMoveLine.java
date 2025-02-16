package cm.xenonbyte.gestitre.domain.stock.entity;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.City;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Quantity;
import cm.xenonbyte.gestitre.domain.common.vo.Reference;
import cm.xenonbyte.gestitre.domain.common.vo.ZipCode;
import cm.xenonbyte.gestitre.domain.shareholder.vo.AccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveId;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineId;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineState;
import cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineType;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.stock.vo.StockMoveLineState.*;

/**
 * @author bamk
 * @version 1.0
 * @since 16/02/2025
 */
public final class StockMoveLine extends BaseEntity<StockMoveLineId> {
    private final AccountNumber accountNumber;
    private final Name name;
    private final StockMoveLineType type;
    private final ShareHolderId shareHolderId;
    private final Quantity quantity;
    private ZonedDateTime createdDate;
    private StockMoveId stockMoveId;
    private City city;
    private ZipCode zipCode;
    private Administrator administrator;
    private Reference reference;
    private StockMoveLineState state;

    public StockMoveLine(
            final AccountNumber accountNumber,
            final Name name,
            final StockMoveLineType type,
            final ShareHolderId shareHolderId,
            final Quantity quantity
    ) {
        this.accountNumber = Objects.requireNonNull(accountNumber);
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.shareHolderId = Objects.requireNonNull(shareHolderId);
        this.quantity = Objects.requireNonNull(quantity);
    }

    private StockMoveLine(Builder builder) {
        setId(builder.id);
        accountNumber = builder.accountNumber;
        name = builder.name;
        type = builder.type;
        shareHolderId = builder.shareHolderId;
        quantity = builder.quantity;
        createdDate = builder.createdDate;
        stockMoveId = builder.stockMoveId;
        city = builder.city;
        zipCode = builder.zipCode;
        administrator = builder.administrator;
        reference = builder.reference;
        state = builder.state;
    }

    public static Builder builder() {
        return new Builder();
    }

    public StockMoveLine initializeDefaults(
            StockMoveId stockMoveId,
            Reference reference,
            Integer lineIndex
    ) {
        setId(new StockMoveLineId(UUID.randomUUID()));
        this.createdDate = ZonedDateTime.now().withZoneSameInstant(Timezone.getCurrentZoneId());
        this.stockMoveId = stockMoveId;
        this.reference = reference.concat(lineIndex.toString());
        this.state = DRAFT;
        return this;
    }

    public void validateMandatoryFields() {
        Assert.field("Account number", accountNumber)
                .notNull(accountNumber);

        Assert.field("Quantity", quantity)
                .notNull(quantity);

        Assert.field("Name", name)
                .notNull(name);

        Assert.field("Type", type)
                .notNull(type);

        Assert.field("ShareHolder", shareHolderId)
                .notNull(shareHolderId);
    }


    public static final class Builder {
        private StockMoveLineId id;
        private AccountNumber accountNumber;
        private Name name;
        private StockMoveLineType type;
        private ShareHolderId shareHolderId;
        private Quantity quantity;
        private ZonedDateTime createdDate;
        private StockMoveId stockMoveId;
        private City city;
        private ZipCode zipCode;
        private Administrator administrator;
        private Reference reference;
        private StockMoveLineState state;

        private Builder() {
        }

        public Builder id(StockMoveLineId val) {
            id = val;
            return this;
        }

        public Builder accountNumber(AccountNumber val) {
            accountNumber = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder type(StockMoveLineType val) {
            type = val;
            return this;
        }

        public Builder shareHolderId(ShareHolderId val) {
            shareHolderId = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder createdDate(ZonedDateTime val) {
            createdDate = val;
            return this;
        }

        public Builder stockMoveId(StockMoveId val) {
            stockMoveId = val;
            return this;
        }

        public Builder city(City val) {
            city = val;
            return this;
        }

        public Builder zipCode(ZipCode val) {
            zipCode = val;
            return this;
        }

        public Builder administrator(Administrator val) {
            administrator = val;
            return this;
        }

        public Builder reference(Reference val) {
            reference = val;
            return this;
        }

        public Builder state(StockMoveLineState val) {
            state = val;
            return this;
        }

        public StockMoveLine build() {
            return new StockMoveLine(this);
        }
    }
}
