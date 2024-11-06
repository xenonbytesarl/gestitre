package cm.xenonbyte.gestitre.domain.company;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainConflictException;

/**
 * @author bamk
 * @version 1.0
 * @since 06/11/2024
 */
public final class CompanyWebSiteUrlConflictException extends BaseDomainConflictException {
    public static final String COMPANY_WEB_SITE_URL_CONFLICT = "CompanyWebSiteUrlConflict.1";

    public CompanyWebSiteUrlConflictException(Object[] args) {
        super(COMPANY_WEB_SITE_URL_CONFLICT, args);
    }
}
