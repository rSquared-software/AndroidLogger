package com.rafalzajfert.androidlogger.textview;

import com.rafalzajfert.androidlogger.Level;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
public class TextViewLoggerConfig {
	Boolean enabled;
	boolean eachInNewLine;
	Level logLevel;
	String tag;

	TextViewLoggerConfig() {
	}

	/**
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
	public TextViewLoggerConfig tag(String tag) {
		this.tag = tag;
		return this;
	}

	/**
	 * Set logger enabled or disabled<br>
	 * If the logger is disabled messages will not be logged
	 */
	public TextViewLoggerConfig enabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	/**
	 * Minimal {@link com.rafalzajfert.androidlogger.Level Level} of message to sent
	 */
	public TextViewLoggerConfig logLevel(Level level) {
		this.logLevel = level;
		return this;
	}

	/**
	 * Minimal {@link com.rafalzajfert.androidlogger.Level Level} of message to sent
	 */
	public TextViewLoggerConfig setEachInNewLine(boolean eachInNewLine) {
		this.eachInNewLine = eachInNewLine;
		return this;
	}
}
