package com.rafalzajfert.androidlogger;

import android.text.TextUtils;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Logger class
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
public abstract class StandardLogger extends Logger {

	/**
	 * If your Logger not provide own tag then format this Method should return {@link #getFormattedTag()}.<br/>
	 * To format your tag you can use {@link #formatTag(String)} method.
	 */
	protected abstract String getTag();

	/**
	 * @return formatted global tag from configuration
	 */
	protected String getFormattedTag() {
		return formatTag(Logger.globalConfig().tag);
	}

	/**
	 * @return tag with replaced constant tags
	 */
	protected static String formatTag(String tag) {
		if (TextUtils.isEmpty(tag)) {
			return "";
		}

		Map<String, String> map = LoggerUtils.getStackTraceFieldMap();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				tag = tag.replace(entry.getKey(), entry.getValue());
			}
		}
		return tag;
	}

	/**
	 * @return default return formatted (using {@link #formatMessage(Object)})  message.
	 */
	@Override
	protected String getMessage(Object msg) {
		return formatMessage(msg);
	}

	/**
	 * @return message with replaced constant tags
	 */
	protected static String formatMessage(Object msg) {
		if (!(msg instanceof String)) {
			return msg + "";
		}
		String message = (String) msg;
		if (TextUtils.isEmpty(message)) {
			return "";
		}

		Map<String, String> map = LoggerUtils.getStackTraceFieldMap();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				message = message.replace(entry.getKey(), entry.getValue());
			}
		}
		return message;
	}

	protected static Map<String, String> getStackTraceFieldMap() {
		return LoggerUtils.getStackTraceFieldMap();
	}

	protected static String getSimpleClassName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.SIMPLE_CLASS_NAME);
	}

	protected static String getFullClassName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FULL_CLASS_NAME);
	}

	protected static String getFileName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FILE_NAME);
	}

	protected static String getMethodName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.METHOD_NAME);
	}

	protected static String getLineNumber() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.LINE_NUMBER);
	}
}
