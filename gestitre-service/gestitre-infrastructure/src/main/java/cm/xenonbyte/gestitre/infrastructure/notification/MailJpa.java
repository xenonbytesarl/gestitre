package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.infrastructure.common.Auditable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author bamk
 * @version 1.0
 * @since 14/03/2025
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_mail")
public class MailJpa extends Auditable {
    @Column(name = "c_to", nullable = false)
    private String to;
    @Column(name = "c_from", nullable = false)
    private String from;
    @Column(name = "c_cc", length = 512)
    private String cc;
    @Column(name = "c_subject", length = 512, nullable = false)
    private String subject;
    @Column(name = "c_message", columnDefinition = "TEXT")
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(name = "c_state", nullable = false)
    private MailStateJpa state;
    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", nullable = false)
    private MailTemplateTypeJpa type;
    @Column(name = "c_created_at", nullable = false)
    private ZonedDateTime createdAt;
    @Column(name = "c_send_at")
    private ZonedDateTime sendAt;
    @Column(name = "c_attempt_to_send")
    private BigInteger attemptToSend;
    @ManyToOne
    @JoinColumn(name = "c_mail_template_id", nullable = false)
    private MailTemplateJpa mailTemplate;
    @ManyToOne
    @JoinColumn(name = "c_mail_server_id", nullable = false)
    private MailServerJpa mailServer;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "t_mail_attribute",
            joinColumns = {@JoinColumn(name = "c_mail_id", referencedColumnName = "c_id")}
    )
    @MapKeyColumn(name = "c_attribute_key")
    @Column(name = "c_attribute_value")
    private Map<String, String> attributes;
}
