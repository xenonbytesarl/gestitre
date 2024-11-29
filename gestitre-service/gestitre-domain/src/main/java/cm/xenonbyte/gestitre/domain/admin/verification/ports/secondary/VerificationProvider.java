package cm.xenonbyte.gestitre.domain.admin.verification.ports.secondary;

import cm.xenonbyte.gestitre.domain.admin.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationProvider {
    String generateUrl(Url url, VerificationType type);

    String generateCode();
}
