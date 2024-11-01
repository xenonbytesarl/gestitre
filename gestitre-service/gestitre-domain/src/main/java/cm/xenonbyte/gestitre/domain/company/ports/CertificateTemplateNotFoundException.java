package cm.xenonbyte.gestitre.domain.company.ports;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CertificateTemplateNotFoundException extends BaseDomainNotFoundException {
    public static final String CERTIFICATE_TEMPLATE_NOT_FOUND = "CertificateTemplateNotFoundException.1";

    public CertificateTemplateNotFoundException(Object[] args) {
        super(CERTIFICATE_TEMPLATE_NOT_FOUND, args);
    }
}
