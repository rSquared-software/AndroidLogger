package com.rafalzajfert.androidlogger.textview;

import android.widget.TextView;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to LogCat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.1.0 (15/04/2015)
 */
public class TextViewLogger extends StandardLogger {

	private TextViewLoggerConfig config;

	private TextView textView;

	public TextViewLogger(TextView textView) {
		this.textView = textView;
		config = new TextViewLoggerConfig.Builder().build();
	}

	/**
	 * Configure logger properties
	 */
	public void setConfiguration(TextViewLoggerConfig config) {
		this.config = config;
	}

	/**
	 * Returns Logger configuration
	 */
	public TextViewLoggerConfig getConfiguration() {
		return this.config;
	}

	@Override
	protected String getTag() {
		if (config == null || config.tag == null) {
			return getFormattedTag();
		} else {
			return formatTag(config.getTag());
		}
	}

	@Override
	protected boolean canLogMessage(Level level) {
		return config == null || (config.isEnabled() && config.isLevelAllowed(level));
	}

	@Override
	protected void printError(String message) {
		append("E", message);
	}

	@Override
	protected void printInfo(String message) {
		append("I", message);
	}

	@Override
	protected void printDebug(String message) {
		append("D", message);
	}

	@Override
	protected void printVerbose(String message) {
		append("V", message);
	}

	@Override
	protected void printWarning(String message) {
		append("W", message);
	}


	private void append(String type, String message) {
		textView.append((textView.length() > 0 ? "\n" : "") + type + " " + getTag() + " " + message);
	}

}
