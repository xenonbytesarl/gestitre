package cm.xenonbyte.gestitre.infrastructure.verification;

import cm.xenonbyte.gestitre.infrastructure.admin.UserJpa;
import cm.xenonbyte.gestitre.infrastructure.common.Auditable;
import cm.xenonbyte.gestitre.infrastructure.notification.MailServerJpa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

import static jakarta.persistence.EnumType.STRING;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_verification")
public class VerificationJpa extends Auditable {

    @Enumerated(STRING)
    @Column(name = "c_type", nullable = false, length = 32)
    private VerificationTypeJpa type;
    @Column(name = "c_code", length = 128)
    private String code;
    @Column(name = "c_url", length = 512)
    private String url;
    @Column(name = "c_email", nullable = false, length = 128)
    private String email;
    @Column(name = "c_expired_at")
    private ZonedDateTime expiredAt;
    @Column(name = "c_creation_at", nullable = false)
    private ZonedDateTime creationAt;
    @Enumerated(STRING)
    @Column(name = "c_status", nullable = false, length = 32)
    private VerificationStatusJpa status;
    @ManyToOne
    @JoinColumn(name = "c_user_id")
    private UserJpa userJpa;
    @ManyToOne
    @JoinColumn(name = "c_mail_server_id")
    private MailServerJpa mailServerJpa;
}
