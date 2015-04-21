package com.rafalzajfert.androidlogger;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 */
public class LoggerConfig {
	Map<String, Logger> loggers = new HashMap<>();

	boolean enabled = true;

	Level logLevel = Level.DEBUG;

	String tag = Logger.PARAM_SIMPLE_CLASS_NAME + "(" + Logger.PARAM_LINE_NUMBER + ")";

	String separator = Logger.SPACE;

	String throwableSeparator = Logger.NEW_LINE;

	private LoggerConfig(Map<String, Logger> loggers, boolean enabled, Level logLevel, String tag, String separator,
	                       String throwableSeparator) {
		this.loggers = loggers;
		this.enabled = enabled;
		this.logLevel = logLevel;
		this.tag = tag;
		this.separator = separator;
		this.throwableSeparator = throwableSeparator;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public String getTag() {
		return tag;
	}

	public String getSeparator() {
		return separator;
	}

	public String getThrowableSeparator() {
		return throwableSeparator;
	}

	public boolean isLevelAllowed(Level level) {
		return level.ordinal() >= logLevel.ordinal();
	}

	/**
	 * Add new {@link com.rafalzajfert.androidlogger.Logger Logger} to list
	 * @param logger
	 */
	public void addLogger(Logger logger) {
		logger.loggerTag = logger.getClass().getSimpleName();
		addLogger(logger.getClass().getSimpleName(), logger);
	}

	/**
	 * Add new {@link com.rafalzajfert.androidlogger.Logger Logger} to list
	 * @param logger
	 */
	public void addLogger(String loggerTag, Logger logger) {
		logger.loggerTag = loggerTag;
		loggers.put(loggerTag, logger);
	}

	/**
	 * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 * @param tag
	 */
	public void removeLogger(String tag) {
		loggers.remove(tag);
	}

	/**
	 * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 * @param logger
	 */
	public void removeLogger(Logger logger) {
		loggers.remove(logger.loggerTag);
	}

	/**
	 * Get {@link com.rafalzajfert.androidlogger.Logger Logger} instance
	 * @param tag
	 */
	public Logger getLogger(String tag) {
		return loggers.get(tag);
	}

	/**
	 * Remove all {@link com.rafalzajfert.androidlogger.Logger Loggers}
	 */
	public void clearLoggers() {
		loggers.clear();
	}

	public static class Builder{
		private Map<String, Logger> loggers = new HashMap<>();
		private boolean enabled = true;
		private Level logLevel = Level.DEBUG;
		private String tag = Logger.PARAM_SIMPLE_CLASS_NAME + "(" + Logger.PARAM_LINE_NUMBER + ")";
		private String separator = Logger.SPACE;
		private String throwableSeparator = Logger.NEW_LINE;

		public LoggerConfig build(){
			return new LoggerConfig(loggers, enabled, logLevel, tag, separator, throwableSeparator);
		}

		/**
		 * Add logger to which you want send messages<br>
		 *
		 * @param logger
		 * @return Config instance
		 */
		public Builder addLogger(Logger logger) {
			logger.loggerTag = logger.getClass().getSimpleName();
			this.loggers.put(logger.loggerTag, logger);
			return this;
		}

		/**
		 * Add logger to which you want send messages<br>
		 *
		 * @param logger
		 * @return Config instance
		 */
		public Builder addLogger(String loggerTag, Logger logger) {
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
		public Builder tag(String tag) {
			this.tag = tag;
			return this;
		}

		/** Set logger enabled or disabled<br>
		 * If the logger is disabled messages will not be logged */
		public Builder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		/**
		 * Minimal {@link com.rafalzajfert.androidlogger.Level Level} of message to sent
		 */
		public Builder logLevel(Level level) {
			this.logLevel = level;
			return this;
		}

		/**
		 * String used to separate message parameters
		 */
		public Builder separator(String separator) {
			this.separator = separator;
			return this;
		}

		/**
		 * String used to separate message and {@link Throwable} log
		 */
		public Builder throwableSeparator(String throwableSeparator) {
			this.throwableSeparator = throwableSeparator;
			return this;
		}
	}
}
