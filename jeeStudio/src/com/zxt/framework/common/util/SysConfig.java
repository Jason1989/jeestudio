package com.zxt.framework.common.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SysConfig {
	private static final ResourceBundle messages = ResourceBundle.getBundle("SysConfig");

	public static String getString(String key) {
		try {
			return messages.getString(key);
		} catch (MissingResourceException ex) {
		}
		return '!' + key + '!';
	}

	public static String getString(String key, Object[] args) {
		try {
			return MessageFormat.format(messages.getString(key), args);
		} catch (MissingResourceException ex) {
		}
		return '!' + key + '!';
	}

	public static String getString(String key, Object arg) {
		return getString(key, new Object[] { arg });
	}
}