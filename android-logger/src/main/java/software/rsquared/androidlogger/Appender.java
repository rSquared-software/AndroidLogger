package software.rsquared.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Calendar;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Rafał Zajfert
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Appender {
	private static final int CHUNK_SIZE = 3000;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private String appenderId;


	protected String getSimpleClassName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.SIMPLE_CLASS_NAME);
	}

	protected String getFullClassName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FULL_CLASS_NAME);
	}

	protected String getFileName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FILE_NAME);
	}

	protected String getMethodName() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.METHOD_NAME);
	}

	protected String getLineNumber() {
		return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.LINE_NUMBER);
	}

	/**
	 * returns {@link Level} name
	 */
	protected String getLevelName(@NonNull Level level) {
		return level.name();
	}

	/**
	 * returns first letter of the {@link Level}
	 */
	protected String getLevelShortName(@NonNull Level level) {
		return level.name().substring(0, 1);
	}

	protected String getTime() {
		return String.format(LoggerConfig.getInstance().getTimePattern(), Calendar.getInstance());
	}

	/**
	 * Append log message
	 */
	protected abstract void append(Level level, String tag, String message);

	protected void append(Level level, Tag tag, Object message, Throwable throwable) {
		String msg = getMessage(message, level);

		StringBuilder builder = new StringBuilder();
		if (!TextUtils.isEmpty(msg)) {
			builder.append(msg);
		}

		if (throwable != null) {
			if (builder.length() > 0) {
				builder.append(getThrowableSeparator());
			}
			builder.append(LoggerUtils.throwableToString(throwable, logWithStackTrace()));
		}

		if (builder.length() == 0) {
			if (msg == null && throwable == null) {
				builder.append("null");
			} else {
				builder.append("[empty log message]");
			}
		}
		if (builder.length() > 0) {
			String text = builder.toString();
			String formattedTag = getTag(level, tag);
			if (text.length() > CHUNK_SIZE) {
				for (String line : WordUtils.wrap(text, CHUNK_SIZE)) {
					append(level, formattedTag, line);
				}
			} else {
				append(level, formattedTag, text);
			}
		}
	}

	@Nullable
	protected String getTag(Level level, @Nullable Tag tag) {
		String result = tag == null ? null : tag.value;
		if (TextUtils.isEmpty(result) && this instanceof ConfigurableAppender) {
			AppenderConfig appenderConfig = ((ConfigurableAppender) this).getConfig();
			if (appenderConfig != null) {
				result = appenderConfig.getTag();
			}
		}
		if (TextUtils.isEmpty(result)) {
			result = LoggerConfig.getInstance().getDefaultTag();
		}
		if (TextUtils.isEmpty(result)) {
			return null;
		}
		return LoggerUtils.formatTag(result, level);
	}

	protected boolean logWithStackTrace() {
		Config config = LoggerConfig.getInstance();
		Config appenderConfig = null;
		if (this instanceof ConfigurableAppender) {
			appenderConfig = ((ConfigurableAppender) this).getConfig();
		}

		return (appenderConfig == null || appenderConfig.logThrowableWithStackTrace) || config.logThrowableWithStackTrace;
	}

	/**
	 * formatted message
	 */
	protected String getMessage(Object msg, Level level) {
		if (msg == null) {
			return null;
		} else {
			return LoggerUtils.formatMessage(msg, level);
		}
	}

	protected String getThrowableSeparator() {
		String separator = null;
		if (this instanceof ConfigurableAppender) {
			Config appenderConfig = ((ConfigurableAppender) this).getConfig();
			if (appenderConfig != null) {
				separator = appenderConfig.getThrowableSeparator();
			}
		}
		if (separator == null) {
			separator = LoggerConfig.getInstance().getThrowableSeparator();
		}

		return separator;
	}
}
