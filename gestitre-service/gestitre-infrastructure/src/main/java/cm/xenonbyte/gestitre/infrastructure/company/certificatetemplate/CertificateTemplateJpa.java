package cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate;

import cm.xenonbyte.gestitre.infrastructure.common.Audit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_certificate_template")
public class CertificateTemplateJpa extends Audit {


    @Column(name = "c_name", nullable = false, unique = true, length = 64)
    private String name;
    @Column(name = "c_active", nullable = false)
    private Boolean active;


}
