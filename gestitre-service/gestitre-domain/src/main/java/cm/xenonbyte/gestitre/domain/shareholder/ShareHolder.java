package cm.xenonbyte.gestitre.domain.shareholder;

import cm.xenonbyte.gestitre.domain.common.entity.AggregateRoot;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.City;
import cm.xenonbyte.gestitre.domain.common.vo.Country;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Money;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.ZipCode;
import cm.xenonbyte.gestitre.domain.shareholder.vo.AccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.AccountType;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator;
import cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Representative;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderId;
import cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderType;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Successor;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 03/02/2025
 */
public class ShareHolder extends AggregateRoot<ShareHolderId> {
    private final Name name;
    private final AccountNumber accountNumber;
    private final AccountType accountType;
    private final Country taxResidence;
    private final Money initialBalance;
    private BankAccountNumber bankAccountNumber;
    private Administrator administrator;
    private Email email;
    private Phone phone;
    private City city;
    private ZipCode zipCode;
    private ShareHolderType shareHolderType;
    private Representative representative;
    private Successor successor;
    private ZonedDateTime createdDate;
    private TenantId tenantId;
    private Active active;

    public ShareHolder(
            Name name,
            AccountNumber accountNumber,
            AccountType accountType,
            Country taxResidence,
            Money initialBalance
    ) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.taxResidence = taxResidence;
        this.initialBalance = initialBalance;
    }

    private ShareHolder(Builder builder) {
        setId(builder.id);
        name = builder.name;
        administrator = builder.administrator;
        city = builder.city;
        accountNumber = builder.accountNumber;
        accountType = builder.accountType;
        taxResidence = builder.taxResidence;
        initialBalance = builder.initialBalance;
        bankAccountNumber = builder.bankAccountNumber;
        email = builder.email;
        phone = builder.phone;
        zipCode = builder.zipCode;
        shareHolderType = builder.shareHolderType;
        representative = builder.representative;
        successor = builder.successor;
        createdDate = builder.createdDate;
        tenantId = builder.tenantId;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeDefaultValues() {
        setId(new ShareHolderId(UUID.randomUUID()));
        this.active = Active.with(true);

    }

    public void validateMandatoryFields() {
        Assert.field("Account Number", accountNumber)
                .notNull(accountNumber);

        Assert.field("Account Type", accountType)
                .notNull(accountType);

        Assert.field("Name", name)
                .notNull(name);

        Assert.field("Tax residence", taxResidence)
                .notNull(taxResidence);

        Assert.field("Initial balance", initialBalance)
                .notNull(initialBalance);

        Assert.field("Creation date", createdDate)
                .notNull(createdDate);

        if(shareHolderType!= null && shareHolderType.equals(ShareHolderType.SUCCESSOR)) {
            Assert.field("Successor", successor)
                    .notNull(successor);
        }

        if(shareHolderType != null && shareHolderType.equals(ShareHolderType.REPRESENTATIVE)) {
            Assert.field("Representative", representative)
                    .notNull(representative);
        }
    }

    public Name getName() {
        return name;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public City getCity() {
        return city;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Country getTaxResidence() {
        return taxResidence;
    }

    public Money getInitialBalance() {
        return initialBalance;
    }

    public BankAccountNumber getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }

    public ShareHolderType getShareHolderType() {
        return shareHolderType;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public Successor getSuccessor() {
        return successor;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public Active getActive() {
        return active;
    }

    public static final class Builder {
        private ShareHolderId id;
        private Name name;
        private Administrator administrator;
        private City city;
        private AccountNumber accountNumber;
        private AccountType accountType;
        private Country taxResidence;
        private Money initialBalance;
        private BankAccountNumber bankAccountNumber;
        private Email email;
        private Phone phone;
        private ZipCode zipCode;
        private ShareHolderType shareHolderType;
        private Representative representative;
        private Successor successor;
        private ZonedDateTime createdDate;
        private TenantId tenantId;
        private Active active;

        private Builder() {
        }

        public Builder id(ShareHolderId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder administrator(Administrator val) {
            administrator = val;
            return this;
        }

        public Builder city(City val) {
            city = val;
            return this;
        }

        public Builder accountNumber(AccountNumber val) {
            accountNumber = val;
            return this;
        }

        public Builder accountType(AccountType val) {
            accountType = val;
            return this;
        }

        public Builder taxResidence(Country val) {
            taxResidence = val;
            return this;
        }

        public Builder initialBalance(Money val) {
            initialBalance = val;
            return this;
        }

        public Builder bankAccountNumber(BankAccountNumber val) {
            bankAccountNumber = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder phone(Phone val) {
            phone = val;
            return this;
        }

        public Builder zipCode(ZipCode val) {
            zipCode = val;
            return this;
        }

        public Builder shareHolderType(ShareHolderType val) {
            shareHolderType = val;
            return this;
        }

        public Builder representative(Representative val) {
            representative = val;
            return this;
        }

        public Builder successor(Successor val) {
            successor = val;
            return this;
        }

        public Builder createdDate(ZonedDateTime val) {
            createdDate = val;
            return this;
        }

        public Builder tenantId(TenantId val) {
            tenantId = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public ShareHolder build() {
            return new ShareHolder(this);
        }
    }
}
