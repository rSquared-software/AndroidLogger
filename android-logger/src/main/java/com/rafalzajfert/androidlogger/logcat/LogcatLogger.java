package com.rafalzajfert.androidlogger.logcat;

import android.util.Log;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to Logcat
 * console
 * 
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
public class LogcatLogger extends StandardLogger {

	private final LogcatLoggerConfig config = new LogcatLoggerConfig();

	public LogcatLogger(){
	}

	/**
	 * Returns Logger configuration
	 */
	public LogcatLoggerConfig config() {
		return this.config;
	}

	@Override
	protected String getTag() {
		if (config.tag == null){
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
