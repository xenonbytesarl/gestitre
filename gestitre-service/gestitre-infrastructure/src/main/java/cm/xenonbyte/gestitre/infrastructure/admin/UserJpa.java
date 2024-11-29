package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.infrastructure.common.Tenantable;
import cm.xenonbyte.gestitre.infrastructure.company.CompanyJpa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

/**
 * @author bamk
 * @version 1.0
 * @since 23/11/2024
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class UserJpa extends Tenantable {
    @Column(name = "c_name", nullable = false, length = 128)
    private String name;
    @Column(name = "c_email", nullable = false, unique = true, length = 128)
    private String email;
    @Column(name = "c_password", nullable = false, length = 1024)
    private String password;
    @Column(name = "c_account_enabled", nullable = false)
    private Boolean accountEnabled;
    @Column(name = "c_credential_expired", nullable = false)
    private Boolean credentialExpired;
    @Column(name = "c_account_locked", nullable = false)
    private Boolean accountLocked;
    @Column(name = "c_account_expired", nullable = false)
    private Boolean accountExpired;
    @Column(name = "c_use_mfa", nullable = false)
    private Boolean useMfa;
    @Column(name = "c_failed_login_attempt", nullable = false)
    private Long failedLoginAttempt;
    @ManyToOne
    @JoinColumn(name = "c_company_id")
    private CompanyJpa companyJpa;
    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "t_user_role",
            joinColumns = @JoinColumn(name = "c_user_id", referencedColumnName = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "c_role_id", referencedColumnName = "c_id")
    )
    private Set<RoleJpa> rolesJpa;
}
