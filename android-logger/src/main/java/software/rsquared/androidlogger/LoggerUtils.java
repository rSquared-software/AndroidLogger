/*
 * Copyright 2017 rSquared s.c. R. Orlik, R. Zajfert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package software.rsquared.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafal Zajfert
 */
abstract class LoggerUtils {

	@SafeVarargs
	static <T> String array2String(@NonNull String separator, T... array) {
		if (array == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (T el : array) {
			if (builder.length() > 0) {
				builder.append(separator);
			}
			builder.append(el);
		}
		return builder.toString();
	}

	@Nullable
	static String getStackTraceField(@NonNull StackTraceField field) {
		StackTraceElement element = getStackTraceElement();
		if (element == null) {
			return null;
		}
		return getField(element, field);
	}

	/**
	 * @return appenderId with replaced constant tags
	 */
	@NonNull
	static String formatTag(@NonNull String tag, @NonNull Level level) {
		return replaceFormatValues(tag, level);
	}

	/**
	 * @return message with replaced constant tags
	 */
	@NonNull
	static String formatMessage(@NonNull Object msg, @NonNull Level level) {
		return replaceFormatValues(msg + "", level);
	}

	/**
	 * Converts {@link Throwable} to String, if <code>withStackTrace</code> is true it would be full stacktrace of the Throwable, otherwise only message
	 */
	@NonNull
	static String throwableToString(@NonNull Throwable throwable, boolean withStackTrace) {
		if (withStackTrace) {
			try (Writer writer = new StringWriter();
				 PrintWriter printWriter = new PrintWriter(writer);) {
				throwable.printStackTrace(printWriter);
				return writer.toString();
			} catch (IOException ignored) {
			}
		}
		return throwable.getMessage();
	}

	/**
	 * Replace all constant tags with values
	 */
	@NonNull
	private static String replaceFormatValues(@NonNull String text, @NonNull Level level) {
		if (TextUtils.isEmpty(text)) {
			return "";
		}

		Map<String, String> map = getStackTraceFieldMap();
		if (map != null) {
			text = replaceCodeLine(text);
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String target = entry.getKey();
				String replacement = entry.getValue();
				if (target != null && replacement != null) {
					text = text.replace(target, replacement);
				}
			}
		}
		String levelName = level.name();
		if (levelName != null) {
			text = text.replace(Logger.LEVEL, levelName);
			text = text.replace(Logger.SHORT_LEVEL, levelName.substring(0, 1));
		}
		String time = getTime();
		if (time != null) {
			text = text.replace(Logger.CURRENT_TIME, time);
		}

		return text;
	}

	private static String getTime() {
		return String.format(LoggerConfig.getInstance().getTimePattern(), Calendar.getInstance());
	}

	public static String replaceCodeLine(@NonNull String text) {
		return text.replace(Logger.CODE_LINE, "(" + Logger.FILE_NAME + ":" + Logger.LINE_NUMBER + ")");
	}

	@Nullable
	private static Map<String, String> getStackTraceFieldMap() {
		final StackTraceElement element = getStackTraceElement();
		if (element == null) {
			return null;
		}
		Map<String, String> stackMap = new HashMap<>();


		stackMap.put(Logger.CLASS_NAME, getField(element, StackTraceField.SIMPLE_CLASS_NAME));
		stackMap.put(Logger.FULL_CLASS_NAME, getField(element, StackTraceField.FULL_CLASS_NAME));
		stackMap.put(Logger.METHOD_NAME, getField(element, StackTraceField.METHOD_NAME));
		stackMap.put(Logger.FILE_NAME, getField(element, StackTraceField.FILE_NAME));
		stackMap.put(Logger.LINE_NUMBER, getField(element, StackTraceField.LINE_NUMBER));

		return stackMap;
	}

	@Nullable
	private static StackTraceElement getStackTraceElement() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		if (elements == null || elements.length <= 2) {
			return null;
		}

		for (int i = 2; i < elements.length; i++) {
			String className = elements[i].getClassName();
			if (!isLoggerClass(className)) {
				return elements[i];
			}
		}
		return elements[2];
	}

	@NonNull
	private static String getField(@NonNull StackTraceElement element, @NonNull StackTraceField field) {
		switch (field) {
			case FULL_CLASS_NAME:
				return element.getClassName();
			case SIMPLE_CLASS_NAME:
				String className = element.getClassName();
				String[] parts = className.split("\\.");
				return parts.length > 0 ? parts[parts.length-1] : "";
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

	private static boolean isLoggerClass(@NonNull String className) {
		return className.equalsIgnoreCase("software.rsquared.androidlogger.LoggerUtils")
				|| classExtendLogger(className)
				|| classExtendAppender(className);
	}

	/**
	 * Checks if class with specified name extends Logger class
	 */
	private static boolean classExtendLogger(String className) {
		try {
			return Logger.class.isAssignableFrom(Class.forName(className));
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * Checks if class with specified name extends Appender class
	 */
	private static boolean classExtendAppender(String className) {
		try {
			return Appender.class.isAssignableFrom(Class.forName(className));
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	enum StackTraceField {
		SIMPLE_CLASS_NAME, FULL_CLASS_NAME, FILE_NAME, METHOD_NAME, LINE_NUMBER
	}
}
