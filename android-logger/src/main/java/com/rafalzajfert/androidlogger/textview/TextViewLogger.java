package com.rafalzajfert.androidlogger.textview;

import android.widget.TextView;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 */
public class TextViewLogger extends StandardLogger {

	private final TextViewLoggerConfig config = new TextViewLoggerConfig();

	private TextView textView;

	public TextViewLogger(TextView textView) {
		this.textView = textView;
	}

	/**
	 * Returns Logger configuration
	 */
	public TextViewLoggerConfig config() {
		return this.config;
	}

	@Override
	protected String getTag() {
		if (config.tag == null) {
			return getFormattedTag();
		} else {
			return formatTag(config.getTag());
		}
	}

	@Override
	protected boolean canLogMessage(Level level) {
		return config.isEnabled() && config.isLevelAllowed(level);
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


	protected void append(String type, String message) {
		textView.append((textView.length() > 0 ? getMessageSeparator() : "") + type + " " + getTag() + " " + message);
	}

	protected String getMessageSeparator(){
		return config.eachInNewLine ? "\n" : " ";
	}

}
