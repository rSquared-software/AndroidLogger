package com.rafalzajfert.androidlogger;

import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class LoggerConfig {
	private Map<String, Logger> loggers = new HashMap<>();
	private List<Logger> standardLogger = new ArrayList<>();

	boolean enabled = true;

	Level logLevel = Level.DEBUG;

	String tag = Logger.PARAM_SIMPLE_CLASS_NAME + "(" + Logger.PARAM_LINE_NUMBER + ")";

	String separator = Logger.SPACE;

	String throwableSeparator = Logger.NEW_LINE;

	LoggerConfig() {
		standardLogger.add(new LogcatLogger());
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public boolean isLevelAllowed(Level level) {
		return level.ordinal() >= logLevel.ordinal();
	}

	public Collection<Logger> getLoggers() {
		return loggers.isEmpty()? standardLogger : loggers.values();
	}

	/**
	 * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 *
	 * @param tag tag of the logger to remove
	 */
	public LoggerConfig removeLogger(String tag) {
		loggers.remove(tag);
		return this;
	}

	/**
	 * Create new {@link com.github.anrwatchdog.ANRWatchDog} thread.<br/><br/>
	 * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a>
	 */
	public LoggerConfig startANRWatchDog(LoggerANRWatchDog watchDog){
		watchDog.start();
		return this;
	}

	/**
	 * Setup Thread to catch and log all uncaught exceptions<br/>
	 * This method not prevent app crash.
	 * @return
	 */
	public LoggerConfig catchAllExceptions(){
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				Logger.error(ex);
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(10);
			}
		});
		return this;
	}

	/**
	 * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 *
	 * @param logger Logger instance to remove
	 */
	public LoggerConfig removeLogger(Logger logger) {
		loggers.remove(logger.loggerTag);
		return this;
	}

	/**
	 * Get {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 *
	 * @param tag tag of the logger to return
	 */
	public Logger getLogger(String tag) {
		return loggers.get(tag);
	}

	/**
	 * Remove all {@link com.rafalzajfert.androidlogger.Logger Loggers}
	 */
	public LoggerConfig clearLoggers() {
		loggers.clear();
		return this;
	}

	/**
	 * Add logger to which you want send messages<br>
	 *
	 * @param logger instance of Logger to used in global logging eg. {@link com.rafalzajfert.androidlogger.Logger#debug(Object)}
	 * @return Config this config instance
	 */
	public LoggerConfig addLogger(Logger logger) {
		String loggerTag = logger.getClass().getSimpleName() + "_" + System.currentTimeMillis();
		addLogger(loggerTag, logger);
		return this;
	}

	/**
	 * Add logger to which you want send messages<br>
	 *
	 * @param logger instance of Logger to used in global logging eg. {@link com.rafalzajfert.androidlogger
	 * .Logger#debug(Object)}
	 * @return Config this config instance
	 */
	public LoggerConfig addLogger(String loggerTag, Logger logger) {
		logger.loggerTag = loggerTag;
		this.loggers.put(loggerTag, logger);
		return this;
	}

	/**
	 * Tag, used to identify source of a log message,<br>
	 * default is class name with line number<br>
	 * <br>
	 * You can also use auto generated values:<br>
	 * {@link Logger#PARAM_SIMPLE_CLASS_NAME PARAM_SIMPLE_CLASS_NAME}<br>
	 * {@link Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
	 * {@link Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
	 * {@link Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
	 * {@link Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
	 */
	public LoggerConfig tag(String tag) {
		this.tag = tag;
		return this;
	}

	/**
	 * Set logger enabled or disabled<br>
	 * If the logger is disabled messages will not be logged
	 */
	public LoggerConfig enabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/**
	 * Minimal {@link com.rafalzajfert.androidlogger.Level Level} of message to sent
	 */
	public LoggerConfig logLevel(Level level) {
		this.logLevel = level;
		return this;
	}

	/**
	 * String used to separate message parameters
	 */
	public LoggerConfig separator(String separator) {
		this.separator = separator;
		return this;
	}

	/**
	 * String used to separate message and {@link Throwable} log
	 */
	public LoggerConfig throwableSeparator(String throwableSeparator) {
		this.throwableSeparator = throwableSeparator;
		return this;
	}
}
