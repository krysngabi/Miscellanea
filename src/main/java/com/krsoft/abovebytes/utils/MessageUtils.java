package com.krsoft.abovebytes.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author mohamed.dantch
 */
public class MessageUtils {

    private final ResourceBundle resourceBundle;

    private MessageUtils() {
        this.resourceBundle = ResourceBundle.getBundle("messages", Locale.ROOT);
    }

    private static class MessageUtilsHolder {
        private static final MessageUtils instance = new MessageUtils();
    }

    public static MessageUtils getInstance() {
        return MessageUtilsHolder.instance;
    }

    public String getMessage(String key, Object... params) {
        return resourceBundle.containsKey(key)
                ? MessageFormat.format(resourceBundle.getString(key), params)
                : key;
    }
}
