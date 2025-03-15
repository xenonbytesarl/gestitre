package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.infrastructure.common.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_mail_server")
public class MailServerJpa extends Auditable {
    @Column(name = "c_name", nullable = false, unique = true, length = 64)
    private String name;

    @Column(name = "c_from", nullable = false, length = 512)
    private String from;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", nullable = false,  length = 32)
    private MailServerTypeJpa type;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_state", nullable = false,  length = 32)
    private MailServerStateJpa state;

    @Column(name = "c_host", nullable = false, length = 64)
    private String host;

    @Column(name = "c_port", nullable = false)
    private Integer port;

    @Column(name = "c_protocol", nullable = false, length = 64)
    private String protocol;

    @Column(name = "c_username", length = 128)
    private String username;

    @Column(name = "c_password", length = 64)
    private String password;

    @Column(name = "c_creation_at", nullable = false)
    private ZonedDateTime creationAt;

    @Column(name = "c_confirmed_at")
    private ZonedDateTime confirmedAt;

    @Column(name = "c_use_ssl")
    private Boolean useSSL;

    @Column(name = "c_use_auth")
    private Boolean useAuth;

    @Column(name = "c_active")
    private Boolean active;

    @Column(name = "c_is_default")
    private Boolean isDefault;
}
