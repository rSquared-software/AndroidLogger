package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Rafal Zajfert
 * @version 1.1.0 (29/04/2015)
 */
@SuppressWarnings("unused")
public abstract class BaseLoggerConfig<E extends BaseLoggerConfig> {
    protected Level level = Level.VERBOSE;
    protected String tag;
    protected Boolean logThrowableWithStackTrace;

    /**
     * Minimal {@link Level Level} of message to sent. Default it is {@link Level#VERBOSE}
     */
    @NonNull
    public Level getLevel() {
        return level;
    }

    /**
     * Minimal {@link Level Level} of message to sent. Default it is {@link Level#VERBOSE}
     */
    @SuppressWarnings("unchecked")
    public E setLevel(@NonNull Level level) {
        this.level = level;
        return (E) this;
    }

    /**
     * @return true if is allowed or level is not set
     */
    public boolean isLevelAllowed(@NonNull Level level) {
        return level.ordinal() >= this.level.ordinal();
    }

    /**
     * Tag, used to identify source of a log message<br/>
     * <br/>
     * You can also use auto generated values:<br/>
     * {@link Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br/>
     * {@link Logger#PARAM_FULL_CLASS_NAME PARAM_FULL_CLASS_NAME}<br/>
     * {@link Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br/>
     * {@link Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br/>
     * {@link Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}<br/>
     * {@link Logger#PARAM_LEVEL PARAM_LEVEL}<br/>
     * {@link Logger#PARAM_SHORT_LEVEL PARAM_SHORT_LEVEL}<br/>
     * {@link Logger#PARAM_LEVEL PARAM_LEVEL}<br/>
     * {@link Logger#PARAM_SHORT_LEVEL PARAM_SHORT_LEVEL}<br/>
     * {@link Logger#PARAM_TIME PARAM_TIME}<br/>
     * {@link Logger#PARAM_CODE_LINE PARAM_CODE_LINE}
     */
    @SuppressWarnings("unchecked")
    public E setTag(@Nullable String tag) {
        this.tag = tag;
        return (E) this;
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
        return (E) this;
    }

    /**
     * Returns true if full stack trace should be logged
     */
    @Nullable
    public Boolean isLogThrowableWithStackTrace() {
        return logThrowableWithStackTrace;
    }
}
