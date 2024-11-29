package cm.xenonbyte.gestitre.infrastructure.admin.verification;

import cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary.VerificationProvider;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;
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
    public String generateUrl(Url baseUrl, VerificationType type) {
        return String.format("%s/%s/%s", baseUrl.text().value(), type.getType(), secureStrong().nextAlphanumeric(64));
    }

    @Override
    public String generateCode() {
        return secureStrong().nextNumeric(6);
    }
}
