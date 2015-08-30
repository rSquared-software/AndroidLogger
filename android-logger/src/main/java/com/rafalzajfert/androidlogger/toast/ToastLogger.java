package com.rafalzajfert.androidlogger.toast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;
import com.rafalzajfert.androidlogger.LoggerConfig;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class ToastLogger extends Logger {

    private ToastLoggerConfig config = new ToastLoggerConfig();

    private Context context;

    public ToastLogger(Context context) {
        this.context = context;
    }

    /**
     * Logger configuration
     */
    public void setConfig(@NonNull ToastLoggerConfig config) {
        this.config = config;
    }

    @Nullable
    @Override
    protected BaseLoggerConfig getConfig() {
        return config;
    }

    @Override
    protected void print(Level level, String message) {
        Toast.makeText(context, (getTag(level) + " " + message).trim(), config.getDuration()).show();
    }
}
