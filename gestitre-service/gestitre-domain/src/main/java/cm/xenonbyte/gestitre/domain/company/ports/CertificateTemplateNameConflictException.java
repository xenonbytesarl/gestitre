package cm.xenonbyte.gestitre.domain.company.ports;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public final class CertificateTemplateNameConflictException extends BaseDomainConflictException {
    public static final String CERTIFICATE_TEMPLATE_NAME_CONFLICT = "CertificateTemplateNameConflictException.1";

    public CertificateTemplateNameConflictException(Object[] args) {
        super(CERTIFICATE_TEMPLATE_NAME_CONFLICT,args);
    }
}
