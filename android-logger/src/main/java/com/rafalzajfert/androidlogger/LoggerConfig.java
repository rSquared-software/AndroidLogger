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
public class LoggerConfig {
	private Map<String, Logger> loggers = new HashMap<>();
	private List<Logger> standardLogger = new ArrayList<Logger>();

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
	 * @param tag
	 */
	public LoggerConfig removeLogger(String tag) {
		loggers.remove(tag);
		return this;
	}

	/**
	 * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 *
	 * @param logger
	 */
	public LoggerConfig removeLogger(Logger logger) {
		loggers.remove(logger.loggerTag);
		return this;
	}

	/**
	 * Get {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 *
	 * @param tag
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
	 * @param logger
	 * @return Config instance
	 */
	public LoggerConfig addLogger(Logger logger) {
		String loggerTag = logger.getClass().getSimpleName() + "_" + System.currentTimeMillis();
		addLogger(loggerTag, logger);
		return this;
	}

	/**
	 * Add logger to which you want send messages<br>
	 *
	 * @param logger
	 * @return Config instance
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
