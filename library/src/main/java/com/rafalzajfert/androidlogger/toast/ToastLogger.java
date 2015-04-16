package com.rafalzajfert.androidlogger.toast;

import android.content.Context;
import android.widget.Toast;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to LogCat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.1.0 (15/04/2015)
 */
public class ToastLogger extends StandardLogger {

	private ToastLoggerConfig config;

	private Context context;

	public ToastLogger(Context context) {
		this.context = context;
		config = new ToastLoggerConfig.Builder().build();
	}

	/**
	 * Configure logger properties
	 */
	public void setConfiguration(ToastLoggerConfig config) {
		this.config = config;
	}

	/**
	 * Returns Logger configuration
	 */
	public ToastLoggerConfig getConfiguration() {
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
		show(message);
	}

	@Override
	protected void printInfo(String message) {
		show(message);
	}

	@Override
	protected void printDebug(String message) {
		show(message);
	}

	@Override
	protected void printVerbose(String message) {
		show(message);
	}

	@Override
	protected void printWarning(String message) {
		show(message);
	}


	private void show(String message) {
		Toast.makeText(context, message, config.duration).show();
	}


}
