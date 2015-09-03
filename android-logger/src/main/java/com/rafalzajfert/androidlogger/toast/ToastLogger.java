package com.rafalzajfert.androidlogger.toast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

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
public class ToastLogger extends Logger implements Configurable<ToastLoggerConfig> {

    private ToastLoggerConfig config = new ToastLoggerConfig();

    private Context context;

    public ToastLogger(Context context) {
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public ToastLoggerConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(@NonNull ToastLoggerConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void print(Level level, String message) {
        Toast.makeText(context, getTag(level) + PARAM_SPACE + message, config.getDuration()).show();
    }
}
