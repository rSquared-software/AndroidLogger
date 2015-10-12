/*
 * Copyright 2015 Rafal Zajfert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.text.TextUtils;

import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class LoggerConfig extends BaseLoggerConfig<LoggerConfig> implements Config {
    public static final String DATE_PATTERN = "HH:mm:ss:SSS";
    private String separator = Logger.PARAM_SPACE;
    private String throwableSeparator = Logger.PARAM_NEW_LINE;
    private String datePattern = DATE_PATTERN;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
    private final Map<String, Logger> loggers = new HashMap<>();

    /**
     * Tis configuration is created with default {@link LogcatLogger}, if you want you can remove it by calling {@link #removeLogger(String)} with {@link #DEFAULT_LOGGER} tag
     */
    public LoggerConfig() {
    }

    /**
     * Create configuration based on the properties file
     */
    public LoggerConfig(@RawRes int propertiesRes) {
        ConfigReader.read(this, propertiesRes);
    }

    @Override
    protected void read(@NonNull Map<String, String> config) {
        super.read(config);
        if (config.containsKey("separator")) {
            setSeparator(config.get("separator"));
        }
        if (config.containsKey("throwableSeparator")) {
            setThrowableSeparator(config.get("throwableSeparator"));
        }
        if (config.containsKey("datePattern")) {
            setDatePattern(config.get("datePattern"));
        }
        if (config.containsKey("catchUncaughtExceptions")) {
            catchUncaughtExceptions(Boolean.parseBoolean(config.get("catchUncaughtExceptions")));
        }
        if (config.containsKey("useANRWatchDog")) {
            if (Boolean.parseBoolean(config.get("useANRWatchDog"))) {
                useANRWatchDog(new LoggableANRWatchDog());
            }
        }
    }

    /**
     * Tag of the default logger
     */
    public static final String DEFAULT_LOGGER = "default_logger";

    {
        this.loggers.put(DEFAULT_LOGGER, new LogcatLogger());
        this.tag = Logger.PARAM_CODE_LINE;
        this.logThrowableWithStackTrace = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggerConfig setTag(@SuppressWarnings("NullableProblems") @NonNull String tag) {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("Tag for all loggers cannot be empty");
        }
        this.tag = tag;
        return this;
    }

    @NonNull
    @Override
    public String getTag() {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("Tag for all loggers cannot be empty");
        }
        return tag;
    }

    @Override
    public LoggerConfig setLogThrowableWithStackTrace(@SuppressWarnings("NullableProblems") @NonNull Boolean logThrowableWithStackTrace) {
        this.logThrowableWithStackTrace = logThrowableWithStackTrace;
        return this;
    }

    @NonNull
    @Override
    public Boolean isLogThrowableWithStackTrace() {
        if (this.logThrowableWithStackTrace == null) {
            throw new IllegalArgumentException("logThrowableWithStackTrace for all loggers cannot be null");
        }
        return this.logThrowableWithStackTrace;
    }

    /**
     * Get {@link Logger Logger} instance
     *
     * @param tag tag of the logger to return
     */
    @Override
    public Logger getLogger(String tag) {
        return this.loggers.get(tag);
    }

    /**
     * Get all loggers that wil be added in this configuration
     */
    @NonNull
    @Override
    public Collection<Logger> getLoggers() {
        return this.loggers.values();
    }

    /**
     * Add logger to which you want send messages<br/>
     *
     * @param logger instance of Logger to used in global logging eg. {@link Logger#debug(Object)}
     * @return Config this baseConfig instance
     */
    @NonNull
    public LoggerConfig addLogger(@NonNull Logger logger) {
        String loggerTag = logger.getClass().getSimpleName() + "_" + System.currentTimeMillis();
        addLogger(logger, loggerTag);
        return this;
    }

    /**
     * Add logger to which you want send messages<br/>
     *
     * @param logger instance of Logger to used in global logging eg. {@link Logger#debug(Object)}
     * @return Config this baseConfig instance
     */
    @NonNull
    public LoggerConfig addLogger(@NonNull Logger logger, @NonNull String loggerTag) {
        logger.loggerTag = loggerTag;
        this.loggers.put(loggerTag, logger);
        return this;
    }

    /**
     * Remove {@link Logger Logger} instance
     *
     * @param tag tag of the logger to remove
     */
    @NonNull
    @Override
    public LoggerConfig removeLogger(@NonNull String tag) {
        this.loggers.remove(tag);
        return this;
    }

    /**
     * Remove {@link Logger Logger} instance
     *
     * @param logger Logger instance to remove
     */
    @NonNull
    @Override
    public LoggerConfig removeLogger(@NonNull Logger logger) {
        this.loggers.remove(logger.loggerTag);
        return this;
    }

    /**
     * Remove all {@link Logger Loggers}
     */
    @NonNull
    @Override
    public LoggerConfig removeAllLoggers() {
        this.loggers.clear();
        return this;
    }

    /**
     * Create new {@link LoggableANRWatchDog} thread.<br/><br/>
     * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a><br/>
     * <b>NOTE: </b>This should not be used in your final release<br/>
     * <b>NOTE2: </b>Use this carefully because the watchdog will prevent the debugger from hanging execution at breakpoints or exceptions (it will detect the debugging pause as an ANR).
     */
    @NonNull
    public LoggerConfig useANRWatchDog(@NonNull LoggableANRWatchDog watchDog) {
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
        return catchUncaughtExceptions(true);
    }

    /**
     * Setup Thread to catch and log all uncaught exceptions<br/>
     * This method not prevent app crash and it should not be used in your final release.
     */
    @NonNull
    public LoggerConfig catchUncaughtExceptions(boolean catchExceptions) {
        if (catchExceptions) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Logger.error(ex);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(10);
                }
            });
        } else {
            Thread.setDefaultUncaughtExceptionHandler(null);
        }
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
    @Override
    public String getSeparator() {
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
    @Override
    public String getThrowableSeparator() {
        return this.throwableSeparator;
    }

    /**
     * Pattern used to format date with {@link SimpleDateFormat}
     */
    @NonNull
    @Override
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * Pattern used to format date with {@link SimpleDateFormat}. <br/>Default: <code>{@value #DATE_PATTERN}</code>
     * <br/><br/>
     * <b>Note:</b> if you changed DateFormat with {@link #setDateFormat(SimpleDateFormat)} before call this method then it will be overwritten
     */
    public LoggerConfig setDatePattern(@NonNull String datePattern) {
        this.datePattern = datePattern;
        this.dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        return this;
    }

    /**
     * {@link SimpleDateFormat}  used to format log time in the log tag and log message
     */
    @NonNull
    @Override
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * {@link SimpleDateFormat}  used to format log time in the log tag and log message
     * <br/><br/>
     * <b>Note:</b> If you change this parameter then {@link #datePattern} may be unused
     */
    public LoggerConfig setDateFormat(@NonNull SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }
}
