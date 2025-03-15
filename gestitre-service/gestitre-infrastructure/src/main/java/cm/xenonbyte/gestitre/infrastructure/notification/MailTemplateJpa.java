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
@Table(name = "t_mail_template")
public class MailTemplateJpa extends Auditable {
    @Column(name = "c_name", nullable = false, unique = true, length = 64)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "c_type", nullable = false, unique = true, length = 32)
    private MailTemplateTypeJpa type;
    @Column(name = "c_active", nullable = false)
    private Boolean active;
}
