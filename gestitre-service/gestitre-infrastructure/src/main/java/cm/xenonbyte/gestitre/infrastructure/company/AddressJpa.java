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
public  class AddressJpa {
    @Column(name = "c_address_street", length = 128)
    private String street;

    @Column(name = "c_address_city", nullable = false, length = 64)
    private String city;

    @Column(name = "c_address_zip_code", nullable = false, length = 32)
    private String zipCode;

    @Column(name = "c_address_country", nullable = false, length = 64)
    private String country;
}
