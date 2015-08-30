package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Rafal on 2015-08-29.
 */
@SuppressWarnings("unused")
public abstract class BaseLoggerConfig<E extends BaseLoggerConfig> {
    protected Level level = Level.VERBOSE;
    protected String tag;
    protected Boolean logThrowableWithStackTrace;

    /**
     * Minimal {@link com.rafalzajfert.androidlogger.Level Level} of message to sent. Default it is {@link Level#VERBOSE}
     */
    @NonNull
    public Level getLevel() {
        return level;
    }

    /**
     * Minimal {@link com.rafalzajfert.androidlogger.Level Level} of message to sent. Default it is {@link Level#VERBOSE}
     */
    @SuppressWarnings("unchecked")
    public E setLevel(@NonNull Level level) {
        this.level = level;
        return (E)this;
    }

    /**
     * @return true if is allowed or level is not set
     */
    public boolean isLevelAllowed(@NonNull Level level) {
        return level.ordinal() >= this.level.ordinal();
    }

    /**
     * Tag, used to identify source of a log message<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LEVEL PARAM_LEVEL}
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SHORT_LEVEL PARAM_SHORT_LEVEL}
     */
    @SuppressWarnings("unchecked")
    public E setTag(@Nullable String tag) {
        this.tag = tag;
        return (E)this;
    }

    /**
     * Tag, used to identify source of a log message
     */
    @Nullable
    public String getTag() {
        return tag;
    }

    /**
     * If true throwable will be logged with message and full stack trace
     */
    @SuppressWarnings("unchecked")
    public E setLogThrowableWithStackTrace(@Nullable Boolean logThrowableWithStackTrace) {
        this.logThrowableWithStackTrace = logThrowableWithStackTrace;
        return (E)this;
    }

    /**
     * Returns true if full stack trace should be logged
     */
    @Nullable
    public Boolean isLogThrowableWithStackTrace() {
        return logThrowableWithStackTrace;
    }
}
