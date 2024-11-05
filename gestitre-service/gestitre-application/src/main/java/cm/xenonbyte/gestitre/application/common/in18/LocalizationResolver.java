package cm.xenonbyte.gestitre.application.common.in18;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.Locale;

/**
 * @author bamk
 * @version 1.0
 * @since 05/11/2024
 */
@RequestScoped
public final class LocalizationResolver {

    @Context
    private HttpHeaders headers;

    public Locale getLocaleFromRequest() {
        if(headers.getAcceptableLanguages().isEmpty()) {
            return Locale.FRENCH;
        }
        return headers.getAcceptableLanguages().getFirst();
    }
}
