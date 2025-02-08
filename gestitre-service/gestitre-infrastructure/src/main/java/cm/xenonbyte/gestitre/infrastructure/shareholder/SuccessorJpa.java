package cm.xenonbyte.gestitre.infrastructure.shareholder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@Getter
@Setter
@Embeddable
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class SuccessorJpa {
    @Column(name = "c_successor_name", length = 128)
    private String name;

    @Column(name = "c_successor_email", unique = true, length = 128)
    private String email;

    @Column(name = "c_successor_phone", unique = true, length = 32)
    private String phone;
}
