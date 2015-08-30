package com.rafalzajfert.androidlogger.toast;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import static android.widget.Toast.*;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.LoggerConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class ToastLoggerConfig extends BaseLoggerConfig<ToastLoggerConfig> {

    /** @hide */
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    @Duration
    private int duration = Toast.LENGTH_SHORT;

    /**
     * How long to display the message.  Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public ToastLoggerConfig setDuration(@Duration int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * How long to display the message.  Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    @Duration
    public int getDuration() {
        return this.duration;
    }
}
