package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class LoggerConfig extends BaseLoggerConfig<LoggerConfig> {
    private String separator = Logger.SPACE;
    private String throwableSeparator = Logger.NEW_LINE;
    private final Map<String, Logger> loggers = new HashMap<>();

    {
        this.loggers.put("default_logger", new LogcatLogger());
        this.tag = Logger.PARAM_SIMPLE_CLASS_NAME + "(" + Logger.PARAM_LINE_NUMBER + ")";
        this.logThrowableWithStackTrace = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggerConfig setTag(@SuppressWarnings("NullableProblems") @NonNull String tag) {
        if (TextUtils.isEmpty(tag)){
            throw new IllegalArgumentException("Tag for all loggers cannot be empty");
        }
        this.tag = tag;
        return this;
    }

    @NonNull
    @Override
    public String getTag() {
        if (TextUtils.isEmpty(tag)){
            throw new IllegalArgumentException("Tag for all loggers cannot be empty");
        }
        return tag;
    }

    @Override
    public LoggerConfig setLogThrowableWithStackTrace(@SuppressWarnings("NullableProblems") @NonNull Boolean logThrowableWithStackTrace) {
        if (logThrowableWithStackTrace == null){
            throw new IllegalArgumentException("logThrowableWithStackTrace for all loggers cannot be null");
        }
        this.logThrowableWithStackTrace = logThrowableWithStackTrace;
        return this;
    }

    @NonNull
    @Override
    public Boolean isLogThrowableWithStackTrace() {
        if (this.logThrowableWithStackTrace == null){
            throw new IllegalArgumentException("logThrowableWithStackTrace for all loggers cannot be null");
        }
        return this.logThrowableWithStackTrace;
    }

    /**
     * Get {@link com.rafalzajfert.androidlogger.Logger Logger} instance
     *
     * @param tag tag of the logger to return
     */
    public Logger getLogger(String tag) {
        return this.loggers.get(tag);
    }

    /**
     * Get all loggers that wil be added in this configuration
     */
    @NonNull
    public Collection<Logger> getLoggers() {
        return this.loggers.values();
    }

    /**
     * Add logger to which you want send messages<br>
     *
     * @param logger instance of Logger to used in global logging eg. {@link com.rafalzajfert.androidlogger.Logger#debug(Object)}
     * @return Config this config instance
     */
    @NonNull
    public LoggerConfig addLogger(@NonNull Logger logger) {
        String loggerTag = logger.getClass().getSimpleName() + "_" + System.currentTimeMillis();
        addLogger(logger, loggerTag);
        return this;
    }

    /**
     * Add logger to which you want send messages<br>
     *
     * @param logger instance of Logger to used in global logging eg. {@link com.rafalzajfert.androidlogger
     *               .Logger#debug(Object)}
     * @return Config this config instance
     */
    @NonNull
    public LoggerConfig addLogger(@NonNull Logger logger, @NonNull String loggerTag) {
        logger.loggerTag = loggerTag;
        this.loggers.put(loggerTag, logger);
        return this;
    }

    /**
     * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
     *
     * @param tag tag of the logger to remove
     */
    @NonNull
    public LoggerConfig removeLogger(@NonNull String tag) {
        this.loggers.remove(tag);
        return this;
    }

    /**
     * Remove {@link com.rafalzajfert.androidlogger.Logger Logger} instance
     *
     * @param logger Logger instance to remove
     */
    @NonNull
    public LoggerConfig removeLogger(@NonNull Logger logger) {
        this.loggers.remove(logger.loggerTag);
        return this;
    }

    /**
     * Remove all {@link com.rafalzajfert.androidlogger.Logger Loggers}
     */
    @NonNull
    public LoggerConfig removeAllLoggers() {
        this.loggers.clear();
        return this;
    }

    /**
     * Create new {@link com.github.anrwatchdog.ANRWatchDog} thread.<br/><br/>
     * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a><br/>
     * This should not be used in your final release
     */
    @NonNull
    public LoggerConfig useANRWatchDog(@NonNull ANRWatchDog watchDog) {
        //noinspection deprecation
        watchDog.start();
        return this;
    }

    /**
     * Setup Thread to catch and log all uncaught exceptions<br/>
     * This method not prevent app crash and it should not be used in your final release.
     */
    @NonNull
    public LoggerConfig catchUncaughtExceptions() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Logger.error(ex);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        });
        return this;
    }

    /**
     * String used to separate message chunks
     */
    @NonNull
    public LoggerConfig setSeparator(@NonNull String separator) {
        this.separator = separator;
        return this;
    }

    /**
     * String used to separate message chunks
     */
    @NonNull
    public String getSeparator(){
        return this.separator;
    }

    /**
     * String used to separate message and {@link Throwable} log
     */
    @NonNull
    public LoggerConfig setThrowableSeparator(@NonNull String throwableSeparator) {
        this.throwableSeparator = throwableSeparator;
        return this;
    }

    /**
     * String used to separate message and {@link Throwable} log
     */
    @NonNull
    public  String getThrowableSeparator(){
        return this.throwableSeparator;
    }
}
