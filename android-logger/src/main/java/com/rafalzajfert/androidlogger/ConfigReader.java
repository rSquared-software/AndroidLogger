package com.rafalzajfert.androidlogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.text.TextUtils;

import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Config properties reader
 *
 * @author Rafal Zajfert
 * @version 1.0.15 (10/10/2015)
 */
public class ConfigReader {
    private static final String LOGGER_CONFIG_PREFIX = "logger";
    private static final Set<String> RESERVED_PROPERTIES = new HashSet<>(Arrays.asList(LOGGER_CONFIG_PREFIX + ".level", LOGGER_CONFIG_PREFIX + ".separator", LOGGER_CONFIG_PREFIX + ".throwableSeparator", LOGGER_CONFIG_PREFIX + ".tag", LOGGER_CONFIG_PREFIX + ".logThrowableWithStackTrace", LOGGER_CONFIG_PREFIX + ".datePattern", LOGGER_CONFIG_PREFIX + ".catchUncaughtExceptions", LOGGER_CONFIG_PREFIX + ".useANRWatchDog"));
    private final LogcatLogger logger = new LogcatLogger();

    private Map<String, Logger> loggers = new HashMap<>();
    private Map<String, String> configMap = new HashMap<>();

    private ConfigReader(@RawRes int propertiesRes) {
        Properties properties = new Properties();
        loadProperties(properties, propertiesRes);
        String value = properties.getProperty(LOGGER_CONFIG_PREFIX);
        if (value != null) {
            readLoggers(properties, value);
        }
        readBaseConfig(properties);
    }

    public static ConfigReader read(int propertiesRes) {
        return new ConfigReader(propertiesRes);
    }

    public Map<String, Logger> getLoggers() {
        return loggers;
    }

    public Map<String, String> getBaseConfigMap() {
        return configMap;
    }

    private void readBaseConfig(Properties properties) {
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".level");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".separator");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".throwableSeparator");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".tag");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".logThrowableWithStackTrace");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".datePattern");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".catchUncaughtExceptions");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".useANRWatchDog");
    }

    private void loadProperties(Properties properties, @RawRes int propertiesRes) {
        Context context = LoggerUtils.getApplicationContext();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(context.getResources().openRawResource(propertiesRes), "UTF-8");
            properties.load(reader);
        } catch (IOException e) {
            logger.w(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.w(e);
                }
            }
        }
    }

    private void readLoggers(Properties properties, String loggerProperty) {
        String[] loggers = loggerProperty.split(",");
        Map<String, Map<String, String>> loggersMap = createLoggersMap(loggers);
        readProperties(properties, loggersMap);
        createLoggers(loggersMap);
    }

    private void addConfigProperty(Properties properties, String property) {
        String value;
        value = properties.getProperty(property);
        if (value != null) {
            configMap.put(property.substring(LOGGER_CONFIG_PREFIX.length() + 1), value);
        }
    }

    @NonNull
    private Map<String, Map<String, String>> createLoggersMap(String[] loggers) {
        Map<String, Map<String, String>> loggersMap = new HashMap<>();
        for (String logger : loggers) {
            logger = logger.trim();
            checkLoggerName(logger);
            loggersMap.put(logger, new HashMap<String, String>());
        }
        return loggersMap;
    }

    private void readProperties(Properties properties, Map<String, Map<String, String>> loggersMap) {
        String loggerName;
        String propertyName;
        String propertyValue;

        Enumeration<?> propertyKeys = properties.propertyNames();
        while (propertyKeys.hasMoreElements()) {
            final String key = (String) propertyKeys.nextElement();
            if (!RESERVED_PROPERTIES.contains(key) && !LOGGER_CONFIG_PREFIX.equals(key)) {
                loggerName = key.substring(key.indexOf(".") + 1);
                propertyName = "";
                propertyValue = properties.getProperty(key);
                int propertyIdx = loggerName.indexOf(".");
                if (propertyIdx > 0) {
                    propertyName = loggerName.substring(propertyIdx + 1);
                    loggerName = loggerName.substring(0, propertyIdx);
                }
                if (loggersMap.containsKey(loggerName)) {
                    loggersMap.get(loggerName).put(propertyName, propertyValue);
                }
            }
        }
    }

    private void checkLoggerName(String logger) {
        if (TextUtils.isEmpty(logger)) {
            throw new IllegalArgumentException("You must specified logger name");
        }
        if (RESERVED_PROPERTIES.contains(LOGGER_CONFIG_PREFIX + "." + logger)) {
            throw new IllegalArgumentException("Logger cannot be named as " + logger);
        }
    }

    private void createLoggers(Map<String, Map<String, String>> loggersMap) {
        for (Map.Entry<String, Map<String, String>> entry : loggersMap.entrySet()) {
            String clazz = entry.getValue().get("");
            entry.getValue().remove("");
            addLogger(clazz, entry.getKey(), entry.getValue());
        }
    }

    private void addLogger(String loggerClass, String loggerTag, Map<String, String> configMap) {
        //noinspection TryWithIdenticalCatches
        try {
            Class clazz = Class.forName(loggerClass);
            Logger l = (Logger) clazz.newInstance();
            l.loggerTag = loggerTag;
            l.init(configMap);
            loggers.put(loggerTag, l);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Logger class '" + loggerClass + "' not found");
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(loggerClass + " must have public 0 args constructor");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(loggerClass + " must have public 0 args constructor");
        }
    }


}
