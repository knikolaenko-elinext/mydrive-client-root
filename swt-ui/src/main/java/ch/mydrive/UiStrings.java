package ch.mydrive;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by kirill on 14.11.2015.
 */
public class UiStrings {
    private static ResourceBundle STRINGS_BUNDLE = ResourceBundle.getBundle("strings");

    public static String getResourceString(String key) {
        try {
            return STRINGS_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return key;
        } catch (NullPointerException e) {
            return "!" + key + "!";
        }
    }
}
