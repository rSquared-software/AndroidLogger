package com.rafalzajfert.androidlogger.logcat;

import com.rafalzajfert.androidlogger.Level;

/**
 * 
 * @author Rafal Zajfert
 * @version 1.1.0 (15/04/2015)
 */
public class LogCatLoggerConfig {
	Boolean enabled;
	Level logLevel;
	String tag;

	private LogCatLoggerConfig(Boolean enabled, Level logLevel, String tag) {
		this.enabled = enabled;
		this.logLevel = logLevel;
		this.tag = tag;
	}

	/**
	 *
	 * @return true if enabled or is not set
	 */
	public boolean isEnabled() {
		return enabled == null ? true : enabled;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public String getTag() {
		return tag;
	}

	/**
	 * @param level
	 * @return true if is allowed or level is not set
	 */
	public boolean isLevelAllowed(Level level) {
		return logLevel == null || level.ordinal() >= logLevel.ordinal();
	}

	public static class Builder{
		private Boolean enabled;
		private Level logLevel;
		private String tag;

		public LogCatLoggerConfig build(){
			return new LogCatLoggerConfig(enabled, logLevel, tag);
		}

		/**
		 * Tag, used to identify source of a log message,<br>
		 * default is class name with line number<br>
		 * <br>
		 * You can also use auto generated values:<br>
		 * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME PARAM_SIMPLE_CLASS_NAME}<br>
		 * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
		 * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
		 * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
		 * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
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
	}
}
