package cm.xenonbyte.gestitre.domain.company.entity;

import cm.xenonbyte.gestitre.domain.common.entity.AggregateRoot;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyName;
import cm.xenonbyte.gestitre.domain.common.vo.Filename;
import cm.xenonbyte.gestitre.domain.common.vo.Quantity;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.company.vo.Activity;
import cm.xenonbyte.gestitre.domain.company.vo.Capitalization;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;
import cm.xenonbyte.gestitre.domain.company.vo.CompanyManagerName;
import cm.xenonbyte.gestitre.domain.company.vo.EndLicence;
import cm.xenonbyte.gestitre.domain.company.vo.GrossDividendStockUnit;
import cm.xenonbyte.gestitre.domain.company.vo.IrcmRetain;
import cm.xenonbyte.gestitre.domain.company.vo.IsinCode;
import cm.xenonbyte.gestitre.domain.company.vo.LegalForm;
import cm.xenonbyte.gestitre.domain.company.vo.Licence;
import cm.xenonbyte.gestitre.domain.company.vo.NetDividendStock;
import cm.xenonbyte.gestitre.domain.company.vo.NominalValue;
import cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber;
import cm.xenonbyte.gestitre.domain.company.vo.TaxNumber;
import cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl;
import cm.xenonbyte.gestitre.domain.company.vo.address.Address;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Contact;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class Company extends AggregateRoot<CompanyId> {

    private final CompanyName companyName;
    private final CompanyManagerName companyManagerName;
    private final Licence licence;
    private final LegalForm legalForm;
    private final Address address;
    private final Contact contact;
    private final Code code;
    private ZonedDateTime createdDate;
    private Activity activity;
    private RegistrationNumber registrationNumber;
    private CertificateTemplateId certificateTemplateId;
    private TenantId tenantId;
    private WebSiteUrl webSiteUrl;
    private IsinCode isinCode;
    private TaxNumber taxNumber;
    private Filename logoFilename;
    private Filename stampFilename;
    private NetDividendStock netDividendStock;
    private GrossDividendStockUnit grossDividendStockUnit;
    private NominalValue nominalValue;
    private Capitalization capitalization;
    private Quantity stockQuantity;
    private IrcmRetain ircmRetain;
    private EndLicence endLicence;
    private Active active;

    public Company(CompanyName companyName, CompanyManagerName companyManagerName,
                   Licence licence, LegalForm legalForm, Address address, Contact contact, Code code) {
        this.companyName = companyName;
        this.companyManagerName = companyManagerName;
        this.licence = licence;
        this.legalForm = legalForm;
        this.address = address;
        this.contact = contact;
        this.code = code;
    }

    private Company(Builder builder) {
        setId(builder.id);
        companyName = builder.companyName;
        companyManagerName = builder.companyManagerName;
        licence = builder.licence;
        legalForm = builder.legalForm;
        address = builder.address;
        contact = builder.contact;
        code = builder.code;
        createdDate = builder.createdDate;
        activity = builder.activity;
        registrationNumber = builder.registrationNumber;
        certificateTemplateId = builder.certificateTemplateId;
        tenantId = builder.tenantId;
        webSiteUrl = builder.webSiteUrl;
        isinCode = builder.isinCode;
        taxNumber = builder.taxNumber;
        logoFilename = builder.logoFilename;
        stampFilename = builder.stampFilename;
        netDividendStock = builder.netDividendStock;
        grossDividendStockUnit = builder.grossDividendStockUnit;
        nominalValue = builder.nominalValue;
        capitalization = builder.capitalization;
        stockQuantity = builder.stockQuantity;
        ircmRetain = builder.ircmRetain;
        endLicence = builder.endLicence;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CompanyName getCompanyName() {
        return companyName;
    }

    public CompanyManagerName getCompanyManagerName() {
        return companyManagerName;
    }

    public Licence getLicence() {
        return licence;
    }

    public LegalForm getLegalForm() {
        return legalForm;
    }

    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public Code getCode() {
        return code;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Activity getActivity() {
        return activity;
    }

    public RegistrationNumber getRegistrationNumber() {
        return registrationNumber;
    }

    public CertificateTemplateId getCertificateTemplateId() {
        return certificateTemplateId;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public WebSiteUrl getWebSiteUrl() {
        return webSiteUrl;
    }

    public IsinCode getIsinCode() {
        return isinCode;
    }

    public TaxNumber getTaxNumber() {
        return taxNumber;
    }

    public Filename getLogoFilename() {
        return logoFilename;
    }

    public Filename getStampFilename() {
        return stampFilename;
    }

    public NetDividendStock getNetDividendStock() {
        return netDividendStock;
    }

    public GrossDividendStockUnit getGrossDividendStockUnit() {
        return grossDividendStockUnit;
    }

    public NominalValue getNominalValue() {
        return nominalValue;
    }

    public Capitalization getCapitalization() {
        return capitalization;
    }

    public Quantity getStockQuantity() {
        return stockQuantity;
    }

    public IrcmRetain getIrcmRetain() {
        return ircmRetain;
    }

    public EndLicence getEndLicence() {
        return endLicence;
    }

    public Active getActive() {
        return active;
    }

    public void initializeDefaultValues() {
        setId(new CompanyId(UUID.randomUUID()));
        this.active = Active.with(true);

        this.createdDate = ZonedDateTime.now();

        if(nominalValue != null && stockQuantity != null) {
            capitalization = Capitalization.of(nominalValue, stockQuantity);
        }
    }

    public void validateMandatoryFields() {
        Assert.field("Company Name", companyName)
                .notNull(companyName);

        Assert.field("Company Manager", companyManagerName)
                .notNull(companyManagerName);

        Assert.field("Licence", licence)
                .notNull()
                .notNull(licence.name())
                .isOneOf(Arrays.stream(Licence.values()).map(Enum::name).toList(), licence.name());

        Assert.field("Legal Form", legalForm)
                .notNull()
                .notNull(legalForm.name())
                .isOneOf(Arrays.stream(LegalForm.values()).map(Enum::name).toList(), legalForm.name());

        Assert.field("Code", code)
                        .notNull(code);

        Assert.field("Address", address)
                .notNull();

        Assert.field("Email", contact)
                .notNull()
                .notNull(contact.email());
    }

    public void addTenant(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public static final class Builder {
        private CompanyId id;
        private CompanyName companyName;
        private CompanyManagerName companyManagerName;
        private Licence licence;
        private LegalForm legalForm;
        private Address address;
        private Contact contact;
        private Code code;
        private ZonedDateTime createdDate;
        private Activity activity;
        private RegistrationNumber registrationNumber;
        private CertificateTemplateId certificateTemplateId;
        private TenantId tenantId;
        private WebSiteUrl webSiteUrl;
        private IsinCode isinCode;
        private TaxNumber taxNumber;
        private Filename logoFilename;
        private Filename stampFilename;
        private NetDividendStock netDividendStock;
        private GrossDividendStockUnit grossDividendStockUnit;
        private NominalValue nominalValue;
        private Capitalization capitalization;
        private Quantity stockQuantity;
        private IrcmRetain ircmRetain;
        private EndLicence endLicence;
        private Active active;

        private Builder() {
        }

        public Builder id(CompanyId val) {
            id = val;
            return this;
        }

        public Builder companyName(CompanyName val) {
            companyName = val;
            return this;
        }

        public Builder companyManagerName(CompanyManagerName val) {
            companyManagerName = val;
            return this;
        }

        public Builder licence(Licence val) {
            licence = val;
            return this;
        }

        public Builder legalForm(LegalForm val) {
            legalForm = val;
            return this;
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public Builder contact(Contact val) {
            contact = val;
            return this;
        }

        public Builder code(Code val) {
            code = val;
            return this;
        }

        public Builder createdDate(ZonedDateTime val) {
            createdDate = val;
            return this;
        }

        public Builder activity(Activity val) {
            activity = val;
            return this;
        }

        public Builder registrationNumber(RegistrationNumber val) {
            registrationNumber = val;
            return this;
        }

        public Builder certificateTemplateId(CertificateTemplateId val) {
            certificateTemplateId = val;
            return this;
        }

        public Builder tenantId(TenantId val) {
            tenantId = val;
            return this;
        }

        public Builder webSiteUrl(WebSiteUrl val) {
            webSiteUrl = val;
            return this;
        }

        public Builder isinCode(IsinCode val) {
            isinCode = val;
            return this;
        }

        public Builder taxNumber(TaxNumber val) {
            taxNumber = val;
            return this;
        }

        public Builder logoFilename(Filename val) {
            logoFilename = val;
            return this;
        }

        public Builder stampFilename(Filename val) {
            stampFilename = val;
            return this;
        }

        public Builder netDividendStock(NetDividendStock val) {
            netDividendStock = val;
            return this;
        }

        public Builder grossDividendStockUnit(GrossDividendStockUnit val) {
            grossDividendStockUnit = val;
            return this;
        }

        public Builder nominalValue(NominalValue val) {
            nominalValue = val;
            return this;
        }

        public Builder capitalization(Capitalization val) {
            capitalization = val;
            return this;
        }

        public Builder stockQuantity(Quantity val) {
            stockQuantity = val;
            return this;
        }

        public Builder ircmRetain(IrcmRetain val) {
            ircmRetain = val;
            return this;
        }

        public Builder endLicence(EndLicence val) {
            endLicence = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public Company build() {
            return new Company(this);
        }
    }
}
