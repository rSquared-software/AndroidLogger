package com.rafalzajfert.androidlogger.logcat;

import android.util.Log;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to LogCat
 * console
 * 
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 */
public class LogCatLogger extends StandardLogger {

	private LogCatLoggerConfig config;

	public LogCatLogger() {
		config = new LogCatLoggerConfig.Builder().build();
	}

	/**
	 * Configure logger properties
	 */
	public void setConfiguration(LogCatLoggerConfig config) {
		this.config = config;
	}

	/**
	 * Returns Logger configuration
	 */
	public LogCatLoggerConfig getConfiguration() {
		return this.config;
	}

	@Override
	protected String getTag() {
		if (config == null || config.tag == null){
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
		Log.e(getTag(), message);
	}

	@Override
	protected void printInfo(String message) {
		Log.i(getTag(), message);
	}

	@Override
	protected void printDebug(String message) {
		Log.d(getTag(), message);
	}

	@Override
	protected void printVerbose(String message) {
		Log.v(getTag(), message);
	}

	@Override
	protected void printWarning(String message) {
		Log.w(getTag(), message);
	}

}
