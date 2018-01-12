package software.rsquared.androidlogger;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Rafał Zajfert
 */
abstract class Config {

	/**
	 * Map of the {@link Level levels} that should be logged as other {@link Level}.
	 * This is useful on devices that blocked specified levels, e.g. Sony block {@link Level#VERBOSE} and {@link Level#DEBUG}
	 */
	@Getter(AccessLevel.PROTECTED)
	final Map<Level, Level> overwrittenLevels = new HashMap<>();

	/**
	 * Minimal level of the message that should be logged
	 */
	@Getter(AccessLevel.PROTECTED)
	@NonNull
	Level level = Level.VERBOSE;

	/**
	 * if true then all stack trace with throwable will be logged
	 */
	@Getter(AccessLevel.PROTECTED)
	boolean logThrowableWithStackTrace = true;

	/**
	 * Separator between message parts
	 */
	@Getter(AccessLevel.PROTECTED)
	@NonNull
	String separator = Logger.SPACE;

	/**
	 * Separator between message and throwable
	 */
	@Getter(AccessLevel.PROTECTED)
	@NonNull
	String throwableSeparator = Logger.NEW_LINE;

	/**
	 * Chack if current configuration allows to append log
	 */
	public boolean isLevelAllowed(Level level) {
		return level.ordinal() >= this.level.ordinal();
	}

	/**
	 * Read values with config map
	 */
	@CallSuper
	protected void read(@NonNull Map<String, String> config) {
		if (config.containsKey("separator")) {
			separator = config.get("separator");
		}
		if (config.containsKey("throwableSeparator")) {
			throwableSeparator = config.get("throwableSeparator");
		}
		if (config.containsKey("level")) {
			try {
				level = Level.valueOf(config.get("level"));
			} catch (Exception e) {
				throw new IllegalArgumentException("Unknown level: " + config.get("level"));
			}
		}
		if (config.containsKey("logThrowableWithStackTrace")) {
			logThrowableWithStackTrace = Boolean.parseBoolean(config.get("logThrowableWithStackTrace"));
		}
	}
}
