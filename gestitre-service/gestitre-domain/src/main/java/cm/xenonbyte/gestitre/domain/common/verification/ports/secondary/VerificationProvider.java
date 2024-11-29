package cm.xenonbyte.gestitre.domain.common.verification.ports.secondary;

import cm.xenonbyte.gestitre.domain.common.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
public interface VerificationProvider {
    String generateUrl(Url url, VerificationType type, Code code);

    String generateNumericCode(Integer size);
    String generateAlphaNumericCode(Integer size);
}
