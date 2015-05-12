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
	protected void print(Level level, String message) {
		String textTag="";
		switch (level){
			case ERROR:
				textTag = "E";
				break;
			case INFO:
				textTag = "I";
				break;
			case DEBUG:
				textTag = "D";
				break;
			case VERBOSE:
				textTag = "V";
				break;
			case WARNING:
				textTag = "W";
				break;
		}
		append(textTag, message);
	}

	protected void append(String type, String message) {
		switch (config.method){
			case APPEND:
				textView.append((textView.length() > 0 ? getMessageSeparator() : "") + type + " " + getTag() + " " + message);
				break;
			case OVERWRITE:
				textView.setText(type + " " + getTag() + " " + message);
				break;
			case APPEND_START:
				textView.setText(type + " " + getTag() + " " + message + (textView.length() > 0 ? getMessageSeparator()
						: "") + textView.getText());
				break;
		}
	}

	protected String getMessageSeparator(){
		return config.eachInNewLine ? "\n" : " ";
	}

}
