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
	protected void print(Level level, String message) {
		switch (level){
			case ERROR:
				Log.e(getTag(), message);
				break;
			case INFO:
				Log.i(getTag(), message);
				break;
			case DEBUG:
				Log.d(getTag(), message);
				break;
			case VERBOSE:
				Log.v(getTag(), message);
				break;
			case WARNING:
				Log.w(getTag(), message);
				break;
		}
	}
}
