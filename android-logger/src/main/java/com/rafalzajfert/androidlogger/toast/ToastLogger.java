package com.rafalzajfert.androidlogger.toast;

import android.content.Context;
import android.widget.Toast;

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
public class ToastLogger extends StandardLogger {

    private final ToastLoggerConfig config = new ToastLoggerConfig();

    private Context context;

    public ToastLogger(Context context) {
        this.context = context;
    }

    /**
     * Returns Logger configuration
     */
    public ToastLoggerConfig config() {
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
    protected void print(Level level, String tag, String message) {
        Toast.makeText(context, message, config.duration).show();
    }

    /**
     * @param message message to print with logger
     * @deprecated use {@link #print(Level, String)} instead.
     */
    @Deprecated
    protected void show(String message) {
        throw new UnsupportedOperationException("show(String) method is deprecated");
    }


}
