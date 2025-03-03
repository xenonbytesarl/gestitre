package cm.xenonbyte.gestitre.application.shareholder.dto;

import cm.xenonbyte.gestitre.domain.company.entity.Company;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.MAX_SIZE;
import static cm.xenonbyte.gestitre.application.common.ApplicationConstant.POSITIVE_OR_ZERO;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FindShareHoldersResponseView extends ShareHolderResponseView {
    @PositiveOrZero(message = POSITIVE_OR_ZERO)
    private BigDecimal ircmRetain;
    @Size(max = 64, message = MAX_SIZE)
    private String isinCode;

    public FindShareHoldersResponseView addCompanyInfo(Company company) {
        if(company.getIrcmRetain() != null) {
            ircmRetain = company.getIrcmRetain().amount().value();
        }
        if(company.getIsinCode() != null) {
            isinCode = company.getIsinCode().text().value();
        }
        return this;
    }
}
