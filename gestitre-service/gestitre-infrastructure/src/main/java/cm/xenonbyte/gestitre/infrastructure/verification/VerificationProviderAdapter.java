package cm.xenonbyte.gestitre.infrastructure.verification;


import cm.xenonbyte.gestitre.domain.common.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import jakarta.enterprise.context.ApplicationScoped;

import static org.apache.commons.lang3.RandomStringUtils.secureStrong;

/**
 * @author bamk
 * @version 1.0
 * @since 29/11/2024
 */
@ApplicationScoped
public final class VerificationProviderAdapter implements VerificationProvider {
    @Override
    public String generateUrl(Url baseUrl, VerificationType type, Code code) {
        return String.format("%s/%s/%s", baseUrl.text().value(), type.getType(), code.text().value());
    }

    @Override
    public String generateNumericCode(Integer size) {
        return secureStrong().nextNumeric(size);
    }

    @Override
    public String generateAlphaNumericCode(Integer size) {
        return secureStrong().nextAlphanumeric(64);
    }
}
