package cm.xenonbyte.gestitre.infrastructure.company;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public  class ContactJpa {
    @Column(name = "c_contact_phone", unique = true, length = 32)
    private String phone;

    @Column(name = "c_contact_fax", unique = true, length = 32)
    private String fax;

    @Column(name = "c_contact_email", nullable = false, unique = true, length = 128)
    private String email;

    @Column(name = "c_contact_name", nullable = false, length = 64)
    private String name;
}
