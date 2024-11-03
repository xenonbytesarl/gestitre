package cm.xenonbyte.gestitre.application.common.in18;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@RequestScoped
public class LocalizationService {
    @Context
    HttpHeaders headers;

    public String getMessage(String key, Object... arguments) {
       try {
           Locale locale = getLocaleFromRequest();
           ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
           String pattern = bundle.getString(key);
           return MessageFormat.format(pattern, arguments);
       } catch (MissingResourceException exception) {
           Locale locale = getLocaleFromRequest();
           ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
           String pattern = bundle.getString("ResourceBundleKeyNotFoundException.1");
           return MessageFormat.format(pattern, key);
       }
    }

    private Locale getLocaleFromRequest() {
        if(headers.getAcceptableLanguages().isEmpty()) {
            return Locale.FRENCH;
        }
        return headers.getAcceptableLanguages().getFirst();
    }
}
