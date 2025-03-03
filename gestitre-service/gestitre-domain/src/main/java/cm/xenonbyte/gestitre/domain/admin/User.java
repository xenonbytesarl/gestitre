package cm.xenonbyte.gestitre.domain.admin;

import cm.xenonbyte.gestitre.domain.admin.vo.AccountEnabled;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountExpired;
import cm.xenonbyte.gestitre.domain.admin.vo.AccountLocked;
import cm.xenonbyte.gestitre.domain.admin.vo.CredentialExpired;
import cm.xenonbyte.gestitre.domain.admin.vo.FailedLoginAttempt;
import cm.xenonbyte.gestitre.domain.admin.vo.Language;
import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.admin.vo.UseMfa;
import cm.xenonbyte.gestitre.domain.common.entity.AggregateRoot;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.CompanyId;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.common.vo.TenantId;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class User extends AggregateRoot<UserId> {
    private final CompanyId companyId;
    private final Email email;
    private final Name name;
    private final Set<Role> roles;
    private final Timezone timezone;
    private Language language;
    private TenantId tenantId;
    private Password password;
    private Password confirmPassword;
    private AccountEnabled accountEnabled;
    private CredentialExpired credentialExpired;
    private AccountLocked accountLocked;
    private AccountExpired accountExpired;
    private UseMfa useMfa;
    private FailedLoginAttempt failedLoginAttempt;


    public User(
            @Nonnull CompanyId companyId,
            @Nonnull Email email,
            @Nonnull Name name,
            @Nonnull Timezone timezone,
            @Nonnull Set<Role> roles) {
        this.companyId = Objects.requireNonNull(companyId);
        this.email = Objects.requireNonNull(email);
        this.name = Objects.requireNonNull(name);
        this.timezone = Objects.requireNonNull(timezone);
        this.roles = Objects.requireNonNull(roles);
    }

    private User(Builder builder) {
        setId(builder.id);
        companyId = builder.companyId;
        email = builder.email;
        language = builder.language;
        password = builder.password;
        confirmPassword = builder.confirmPassword;
        tenantId = builder.tenantId;
        name = builder.name;
        roles = builder.roles;
        timezone = builder.timezone;
        accountEnabled = builder.accountEnabled;
        credentialExpired = builder.credentialExpired;
        accountLocked = builder.accountLocked;
        accountExpired = builder.accountExpired;
        useMfa = builder.useMfa;
        failedLoginAttempt = builder.failedLoginAttempt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeDefaults(TenantId tenantId) {
        setId(new UserId(UUID.randomUUID()));
        this.tenantId = tenantId;
        this.accountEnabled = AccountEnabled.with(false);
        this.credentialExpired = CredentialExpired.with(false);
        this.accountLocked = AccountLocked.with(false);
        this.accountExpired = AccountExpired.with(false);
        if(this.useMfa == null) {
            this.useMfa = UseMfa.with(true);
        }
        this.failedLoginAttempt = FailedLoginAttempt.of(0L);
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public CompanyId getCompanyId() {
        return companyId;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Password getConfirmPassword() {
        return confirmPassword;
    }

    public Name getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public AccountEnabled getAccountEnabled() {
        return accountEnabled;
    }

    public CredentialExpired getCredentialExpired() {
        return credentialExpired;
    }

    public AccountLocked getAccountLocked() {
        return accountLocked;
    }

    public AccountExpired getAccountExpired() {
        return accountExpired;
    }

    public UseMfa getUseMfa() {
        return useMfa;
    }

    public FailedLoginAttempt getFailedLoginAttempt() {
        return failedLoginAttempt;
    }

    public Language getLanguage() {
        return language;
    }

    public void validatePasswords() {
        Assert.field("Password", password)
                .notNull();

        Assert.field("Confirm Password", confirmPassword)
                .notNull();
    }
    public void validateMandatoryFields() {

        Assert.field("Email", email)
                .notNull();

        Assert.field("Name", name)
                .notNull();

        Assert.field("Timezone", timezone)
                .notNull();

        Assert.field("Language", language)
                .notNull();

        Assert.field("Roles", roles)
                .notNull()
                .minLength(1, roles.size());

        Assert.field("Company ID", companyId)
                .notNull();
    }

    public void validatePassword() {
        if (!password.equals(confirmPassword)) {
            throw new UserPasswordNotMatchException();
        }
    }

    public void activate() {
        accountEnabled = AccountEnabled.with(true);
    }

    public void encryptPassword(Password encryptPassword) {
        this.password = encryptPassword;
    }

    public Boolean isAccountDisabled() {
        return !accountEnabled.value();
    }

    public Boolean isCredentialExpired() {
        return credentialExpired.value();
    }

    public Boolean isAccountExpired() {
        return accountExpired.value();
    }

    public Boolean isAccountLocked() {
        return accountLocked.value();
    }

    public void assignEncryptedPassword(Password encryptedPassword) {
        password = encryptedPassword;
    }

    public void assignTenant(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public static final class Builder {
        private UserId id;
        private TenantId tenantId;
        private CompanyId companyId;
        private Email email;
        private Language language;
        private Password password;
        private Password confirmPassword;
        private Name name;
        private Set<Role> roles;
        private Timezone timezone;
        private AccountEnabled accountEnabled;
        private CredentialExpired credentialExpired;
        private AccountLocked accountLocked;
        private AccountExpired accountExpired;
        private UseMfa useMfa;
        private FailedLoginAttempt failedLoginAttempt;

        private Builder() {
        }

        public Builder id(UserId val) {
            id = val;
            return this;
        }

        public Builder tenantId(TenantId val) {
            tenantId = val;
            return this;
        }

        public Builder companyId(CompanyId val) {
            companyId = val;
            return this;
        }

        public Builder email(Email val) {
            email = val;
            return this;
        }

        public Builder language(Language val) {
            language = val;
            return this;
        }

        public Builder password(Password val) {
            password = val;
            return this;
        }

        public Builder confirmPassword(Password val) {
            confirmPassword = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }

        public Builder timezone(Timezone val) {
            timezone = val;
            return this;
        }

        public Builder accountEnabled(AccountEnabled val) {
            accountEnabled = val;
            return this;
        }

        public Builder credentialExpired(CredentialExpired val) {
            credentialExpired = val;
            return this;
        }

        public Builder accountLocked(AccountLocked val) {
            accountLocked = val;
            return this;
        }

        public Builder accountExpired(AccountExpired val) {
            accountExpired = val;
            return this;
        }

        public Builder useMfa(UseMfa val) {
            useMfa = val;
            return this;
        }

        public Builder failedLoginAttempt(FailedLoginAttempt val) {
            failedLoginAttempt = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
