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

import android.app.Application;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.annotation.XmlRes;
import android.text.TextUtils;

import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class LoggerConfig extends BaseLoggerConfig<LoggerConfig> {
    private static final String LOGGER_CONFIG_PREFIX = "logger";
    private static final Set<String> RESERVED_PROPERTIES = new HashSet<>(Arrays.asList(LOGGER_CONFIG_PREFIX + ".level", LOGGER_CONFIG_PREFIX + ".separator", LOGGER_CONFIG_PREFIX + ".throwableSeparator", LOGGER_CONFIG_PREFIX + ".tag", LOGGER_CONFIG_PREFIX + ".logThrowableWithStackTrace", LOGGER_CONFIG_PREFIX + ".datePattern", LOGGER_CONFIG_PREFIX + ".catchUncaughtExceptions", LOGGER_CONFIG_PREFIX + ".useANRWatchDog"));
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
        loggers.clear();
        Properties properties = new Properties();
        loadProperties(properties, propertiesRes);
        String value = properties.getProperty(LOGGER_CONFIG_PREFIX);
        if (value != null) {
            readLoggers(properties, value);
        }

        Map<String, String> config = new HashMap<>();
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".level");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".separator");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".throwableSeparator");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".tag");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".logThrowableWithStackTrace");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".datePattern");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".catchUncaughtExceptions");
        addConfigProperty(properties, config, LOGGER_CONFIG_PREFIX + ".useANRWatchDog");
        read(config);
    }

    private void loadProperties(Properties properties, @RawRes int propertiesRes) {
        Context context = LoggerUtils.getApplicationContext();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(context.getResources().openRawResource(propertiesRes), "UTF-8");
            properties.load(reader);

        } catch (IOException e) {
            new LogcatLogger().w(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    new LogcatLogger().w(e);
                }
            }
        }
    }

    private void addConfigProperty(Properties properties, Map<String, String> config, String property) {
        String value;
        value = properties.getProperty(property);
        if (value != null) {
            config.put(property.substring(LOGGER_CONFIG_PREFIX.length() + 1), value);
        }
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

    private void readLoggers(Properties properties, String loggerProperty) {
        Enumeration<?> propertyKeys = properties.propertyNames();
        String[] loggers = loggerProperty.split(",");
        Map<String, Map<String, String>> loggersMap = new HashMap<>();
        for (String logger : loggers) {
            logger = logger.trim();
            checkLoggerName(logger);
            loggersMap.put(logger, new HashMap<String, String>());
        }

        while (propertyKeys.hasMoreElements()) {
            final String key = (String) propertyKeys.nextElement();
            if (!RESERVED_PROPERTIES.contains(key) && !LOGGER_CONFIG_PREFIX.equals(key)) {
                String name = key.substring(key.indexOf(".") + 1);
                int propertyIdx = name.indexOf(".");
                if (propertyIdx > 0) {
                    loggersMap.get(name.substring(0, propertyIdx)).put(name.substring(propertyIdx + 1), properties.getProperty(key));
                } else {
                    loggersMap.get(name).put("", properties.getProperty(key));
                }

            }
        }

        for (Map.Entry<String, Map<String, String>> entry : loggersMap.entrySet()) {
            String clazz = entry.getValue().get("");
            entry.getValue().remove("");
            addLogger(clazz, entry);
        }
    }

    private void addLogger(String loggerClass, Map.Entry<String, Map<String, String>> entry) {
        try {
            Class clazz = Class.forName(loggerClass);
            Logger l = (Logger) clazz.newInstance();
            l.init(entry.getValue());
            addLogger(l, entry.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkLoggerName(String logger) {
        if (TextUtils.isEmpty(logger)){
            throw new IllegalArgumentException("You must specified logger name");
        }
        if (RESERVED_PROPERTIES.contains(LOGGER_CONFIG_PREFIX + "." + logger)) {
            throw new IllegalArgumentException("Logger cannot be named as " + logger);
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
    public LoggerConfig removeLogger(@NonNull Logger logger) {
        this.loggers.remove(logger.loggerTag);
        return this;
    }

    /**
     * Remove all {@link Logger Loggers}
     */
    @NonNull
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
    public String getThrowableSeparator() {
        return this.throwableSeparator;
    }

    /**
     * Pattern used to format date with {@link SimpleDateFormat}
     */
    @NonNull
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

    @Override
    public String toString() {
        return "LoggerConfig{" +
                "separator='" + separator + '\'' +
                ", throwableSeparator='" + throwableSeparator + '\'' +
                ", datePattern='" + datePattern + '\'' +
                ", dateFormat=" + dateFormat +
                ", loggers=" + loggers +
                "} " + super.toString();
    }
}
