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
@SuppressWarnings("unused")
public class LogcatLogger extends StandardLogger {

    private final LogcatLoggerConfig config = new LogcatLoggerConfig();

    public LogcatLogger() {
    }

    /**
     * Returns Logger configuration
     */
    public LogcatLoggerConfig config() {
        return this.config;
    }

    @Override
    protected String getTag() {
        if (config.tag == null) {
            return super.getTag();
        } else {
            return formatTag(config.getTag());
        }
    }

    @Override
    protected boolean canLogMessage(Level level) {
        return config.isEnabled() && config.isLevelAllowed(level);
    }

    @Override
    protected void print(Level level, String tag, String message) {
        switch (level) {
            case ERROR:
                Log.e(tag, message);
                break;
            case INFO:
                Log.i(tag, message);
                break;
            case DEBUG:
                Log.d(tag, message);
                break;
            case VERBOSE:
                Log.v(tag, message);
                break;
            case WARNING:
                Log.w(tag, message);
                break;
        }
    }
}
