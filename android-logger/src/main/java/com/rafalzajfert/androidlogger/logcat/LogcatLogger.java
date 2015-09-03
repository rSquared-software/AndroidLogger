package com.rafalzajfert.androidlogger.logcat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;
import com.rafalzajfert.androidlogger.Configurable;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;

/**
 * {@link Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class LogcatLogger extends Logger implements Configurable<LogcatLoggerConfig> {

    private LogcatLoggerConfig config = new LogcatLoggerConfig();

    public LogcatLogger() {
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public LogcatLoggerConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(@NonNull LogcatLoggerConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void print(Level level, String message) {
        String tag = getTag(level);
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
