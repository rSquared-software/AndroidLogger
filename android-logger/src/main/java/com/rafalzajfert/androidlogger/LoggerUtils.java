package com.rafalzajfert.androidlogger;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
class LoggerUtils {

	@SafeVarargs
	final static String array2String(String separator, Object... array) {
		StringBuilder builder = new StringBuilder();
		for (Object t : array) {
			builder.append(t).append(separator);
		}
		if (builder.length() >= separator.length()) {
			builder.deleteCharAt(builder.length() - separator.length());
		}
		return builder.toString();
	}

	enum StackTraceField {
		SIMPLE_CLASS_NAME, FULL_CLASS_NAME, FILE_NAME, METHOD_NAME, LINE_NUMBER
	}

	static Map<String, String> getStackTraceFieldMap() {
		final StackTraceElement element = getStackTraceElement();
		if (element == null) {
			return null;
		}

		return new HashMap<String, String>() {
			private static final long serialVersionUID = -5934767247384989357L;
			{
				put( Logger.PARAM_SIMPLE_CLASS_NAME,
						getField(element, StackTraceField.SIMPLE_CLASS_NAME));
				put(Logger.PARAM_CLASS_NAME,
						getField(element, StackTraceField.FULL_CLASS_NAME));
				put(Logger.PARAM_METHOD_NAME,
						getField(element, StackTraceField.METHOD_NAME));
				put(Logger.PARAM_FILE_NAME, getField(element, StackTraceField.FILE_NAME));
				put(Logger.PARAM_LINE_NUMBER,
						getField(element, StackTraceField.LINE_NUMBER));
			}
		};
	}

	static String getStackTraceField(StackTraceField field) {
		StackTraceElement element = getStackTraceElement();
		if (element == null) {
			return null;
		}
		return getField(element, field);
	}

	private static StackTraceElement getStackTraceElement() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		if (elements == null || elements.length <= 2) {
			return null;
		}

		for (int i = 2; i < elements.length; i++) {
			String className = elements[i].getClassName();
			if (!isLogger(className)) {
				return elements[i];
			}
		}
		return null;
	}

	private static boolean isLogger(String className) {
		return className.startsWith(BuildConfig.APPLICATION_ID) || classExtendLogger(className);
	}

	private static String getField(StackTraceElement element, StackTraceField field) {
		switch (field) {
		case FULL_CLASS_NAME:
			return element.getClassName();
		case SIMPLE_CLASS_NAME:
			return getSimpleName(element.getClassName());
		case FILE_NAME:
			return element.getFileName();
		case METHOD_NAME:
			return element.getMethodName();
		case LINE_NUMBER:
			return element.getLineNumber() + "";
		default:
			return "";
		}
	}

	private static String getSimpleName(String className) {
		int idx = className.lastIndexOf('.');
		boolean canSubstring = idx >= 0 && idx < className.length();
		return canSubstring ? className.substring(idx + 1) : className;
	}

	private static boolean classExtendLogger(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return Logger.class.isAssignableFrom(clazz);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
