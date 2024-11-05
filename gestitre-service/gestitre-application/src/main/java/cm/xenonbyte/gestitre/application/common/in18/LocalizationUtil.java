package cm.xenonbyte.gestitre.application.common.in18;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */

public class LocalizationUtil {

    public static String getMessage(String key, Locale locale, Object... arguments) {
       try {
           ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
           String pattern = bundle.getString(key);
           return MessageFormat.format(pattern, arguments);
       } catch (MissingResourceException exception) {
           ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
           String pattern = bundle.getString("ResourceBundleKeyNotFoundException.1");
           return MessageFormat.format(pattern, key);
       }
    }


}
