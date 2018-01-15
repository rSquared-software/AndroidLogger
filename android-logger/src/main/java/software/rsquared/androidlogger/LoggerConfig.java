package software.rsquared.androidlogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import software.rsquared.androidlogger.logcat.LogcatAppender;

/**
 * @author Rafał Zajfert
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue", "SameParameterValue", "MismatchedQueryAndUpdateOfCollection"})
public class LoggerConfig extends Config  {

	private static LoggerConfig instance;

	private static final String DEFAULT_APPENDER = "__$default_logger$";

	/**
	 * Default pattern to display time.
	 */
	private static final String DEFAULT_TIME_PATTERN = "%1$tH:%1$tM:%1$tS.%1$tL";

	/**
	 * Map of all global appenders, key is identifier of the logger in value
	 */
	@Getter(AccessLevel.PACKAGE)
	private final Map<String, Logger> loggerMap = new HashMap<>();

	/**
	 * default log value that will be used when custom appenderId is not defined
	 */
	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private String defaultTag = Logger.CODE_LINE;

	/**
	 * Pattern of the displayed time
	 */
	@Getter(AccessLevel.PACKAGE)
	@NonNull
	private String timePattern = DEFAULT_TIME_PATTERN;

	private LoggerConfig() {
		enableDefaultAppender(true);
	}

	public static LoggerConfig getInstance() {
		if (instance == null) {
			instance = new LoggerConfig();
		}
		return instance;
	}

	public void fromProperties(Context context, @RawRes int propertiesRes) {
		ConfigPropertiesReader reader = ConfigPropertiesReader.read(context, propertiesRes);
		this.loggerMap.clear();
		this.loggerMap.putAll(reader.getLoggerMap());
		read(reader.getBaseConfigMap());
	}

	/**
	 * Add global appender, key is identifier of the logger in value
	 */
	public LoggerConfig addAppender(@NonNull Appender appender) {
		return addAppender(null, appender);
	}

	/**
	 * Add global appender, key is identifier of the logger in value
	 */
	public LoggerConfig addAppender(String appenderId, @NonNull Appender appender) {
		if (TextUtils.isEmpty(appenderId)) {
			appenderId = appender.getClass().getSimpleName() + "_" + System.currentTimeMillis();
		}
		appender.setAppenderId(appenderId);
		this.loggerMap.put(appenderId, Logger.createWith(appender));
		return this;
	}

	/**
	 * Remove global appender
	 */
	public LoggerConfig removeAppender(@NonNull String appenderId) {
		if (!TextUtils.isEmpty(appenderId)) {
			this.loggerMap.remove(appenderId);
		}
		return this;
	}

	/**
	 * Remove global appender
	 */
	public LoggerConfig removeAppender(@NonNull Logger logger) {
		if (logger instanceof AppenderLogger && !TextUtils.isEmpty(((AppenderLogger) logger).getAppender().getAppenderId())) {
			this.loggerMap.remove(((AppenderLogger) logger).getAppender().getAppenderId());
		}
		return this;
	}

	/**
	 * Remove global appender
	 */
	public LoggerConfig removeAppender(@NonNull Appender appender) {
		return removeAppender(appender.getAppenderId());
	}

	/**
	 * Remove all appenders
	 */
	public LoggerConfig removeAllAppenders() {
		this.loggerMap.clear();
		return this;
	}

	/**
	 * {@link Level Level} that should be logged as other {@link Level}.
	 * This is useful on devices that blocked specified levels, e.g. Sony block {@link Level#VERBOSE} and {@link Level#DEBUG}
	 */
	public LoggerConfig overwriteLevel(@NonNull Level oldLevel, @NonNull Level newLevel) {
		this.overwrittenLevels.put(oldLevel, newLevel);
		return this;
	}

	/**
	 * Remove {@link Level Level} overwriting
	 */
	public LoggerConfig removeLevelOverwriting(@NonNull Level level) {
		this.overwrittenLevels.remove(level);
		return this;
	}

	/**
	 * Remove all {@link Level Level} overwrites
	 */
	public LoggerConfig removeAllLevelOverwrites() {
		this.overwrittenLevels.clear();
		return this;
	}

	/**
	 * Minimal level of the message that should be logged
	 */
	public LoggerConfig setLevel(@NonNull Level level) {
		this.level = level;
		return this;
	}

	/**
	 * default log value that will be used when custom appenderId is not defined
	 */
	public LoggerConfig setDefaultTag(@NonNull String defaultTag) {
		this.defaultTag = defaultTag;
		return this;
	}

	/**
	 * if true then all stack trace with throwable will be logged
	 */
	public LoggerConfig setLogThrowableWithStackTrace(boolean logThrowableWithStackTrace) {
		this.logThrowableWithStackTrace = logThrowableWithStackTrace;
		return this;
	}

	/**
	 * Separator between message parts
	 */
	public LoggerConfig setSeparator(@NonNull String separator) {
		this.separator = separator;
		return this;
	}

	/**
	 * Separator between message and throwable
	 */
	public LoggerConfig setThrowableSeparator(@NonNull String throwableSeparator) {
		this.throwableSeparator = throwableSeparator;
		return this;
	}

	/**
	 * Pattern of the displayed time
	 */
	public LoggerConfig setTimePattern(@NonNull String timePattern) {
		this.timePattern = timePattern;
		return this;
	}

	/**
	 * Enable or disable default logcat appender
	 */
	public LoggerConfig enableDefaultAppender(boolean enable) {
		if (enable) {
			if (!this.loggerMap.containsKey(DEFAULT_APPENDER)) {
				this.loggerMap.put(DEFAULT_APPENDER, new AppenderLogger(new LogcatAppender()));
			}
		} else {
			this.loggerMap.remove(DEFAULT_APPENDER);
		}
		return this;
	}

	/**
	 * Setup Thread to catch and log all uncaught exceptions<p>
	 * This method doesn't prevent app crash and it should not be used in your final release.
	 */
	@NonNull
	public LoggerConfig catchUncaughtExceptions() {
		return catchUncaughtExceptions(true, null);
	}

	/**
	 * Setup Thread to catch and log all uncaught exceptions<p>
	 * This method doesn't prevent app crash and it should not be used in your final release.
	 */
	@NonNull
	public LoggerConfig catchUncaughtExceptions(boolean catchUncaughtExceptions) {
		return catchUncaughtExceptions(catchUncaughtExceptions, null);
	}

	/**
	 * Setup Thread to catch and log all uncaught exceptions<p>
	 * This method doesn't prevent app crash and it should not be used in your final release.
	 */
	@NonNull
	public LoggerConfig catchUncaughtExceptions(boolean catchUncaughtExceptions, @Nullable LoggerUncaughtExceptionHandler handler) {
		if (catchUncaughtExceptions) {
			Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
				Logger.error(ex);
				if (handler == null || !handler.uncaughtException(thread, ex)) {
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(10);
				}
			});
		} else {
			Thread.setDefaultUncaughtExceptionHandler(null);
		}
		return this;
	}

	/**
	 * Create new {@link LoggableANRWatchDog} thread.<p>
	 * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a><p>
	 * <b>NOTE: </b>This should not be used in your final release<p>
	 * <b>NOTE2: </b>Use this carefully because the watchdog will prevent the debugger with hanging execution at breakpoints or exceptions (it will detect the debugging pause as an ANR).
	 */
	@NonNull
	public LoggerConfig runANRWatchDog(@NonNull LoggableANRWatchDog watchDog) {
		watchDog.start();
		return this;
	}

	/**
	 * Read values with config map
	 */
	@Override
	protected void read(@NonNull Map<String, String> config) {
		super.read(config);
		if (config.containsKey("timePattern")) {
			setTimePattern(config.get("timePattern"));
		}
		if (config.containsKey("catchUncaughtExceptions")) {
			catchUncaughtExceptions(Boolean.parseBoolean(config.get("catchUncaughtExceptions")));
		}
		if (config.containsKey("runANRWatchDog")) {
			if (Boolean.parseBoolean(config.get("runANRWatchDog"))) {
				runANRWatchDog(new LoggableANRWatchDog());
			}
		}
		if (config.containsKey("defaultTag")) {
			setDefaultTag(config.get("defaultTag"));
		}
	}

	public interface LoggerUncaughtExceptionHandler {

		/**
		 * Return true if the handler consumed the exception, false otherwise
		 *
		 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(Thread, Throwable)
		 */
		boolean uncaughtException(Thread thread, Throwable ex);
	}
}
