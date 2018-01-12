package software.rsquared.androidlogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import software.rsquared.androidlogger.logcat.LogcatAppender;

/**
 *
 * @author Rafal Zajfert
 */
public class ConfigPropertiesReader {
    private static final String LOGGER_CONFIG_PREFIX = "logger";
    private static final Set<String> RESERVED_PROPERTIES = new HashSet<>(Arrays.asList(LOGGER_CONFIG_PREFIX + ".level", LOGGER_CONFIG_PREFIX + ".separator", LOGGER_CONFIG_PREFIX + ".throwableSeparator", LOGGER_CONFIG_PREFIX + ".appenderId", LOGGER_CONFIG_PREFIX + ".logThrowableWithStackTrace", LOGGER_CONFIG_PREFIX + ".timePattern", LOGGER_CONFIG_PREFIX + ".catchUncaughtExceptions", LOGGER_CONFIG_PREFIX + ".useANRWatchDog"));
    private final Logger logger = Logger.createWith(new LogcatAppender());

    private Map<String, Logger> loggerMap = new HashMap<>();
    private Map<String, String> configMap = new HashMap<>();

    private ConfigPropertiesReader(Context context, @RawRes int propertiesRes) {
        Properties properties = new Properties();
        loadProperties(context, properties, propertiesRes);
        String value = properties.getProperty(LOGGER_CONFIG_PREFIX);
        if (value != null) {
            readLoggers(properties, value);
        }
        readBaseConfig(properties);
    }

    public static ConfigPropertiesReader read(Context context, int propertiesRes) {
        return new ConfigPropertiesReader(context, propertiesRes);
    }

    public Map<String, Logger> getLoggerMap() {
        return loggerMap;
    }

    public Map<String, String> getBaseConfigMap() {
        return configMap;
    }

    private void readBaseConfig(Properties properties) {
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".level");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".separator");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".throwableSeparator");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".appenderId");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".logThrowableWithStackTrace");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".timePattern");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".catchUncaughtExceptions");
        addConfigProperty(properties, LOGGER_CONFIG_PREFIX + ".useANRWatchDog");
    }

    private void loadProperties(Context context, Properties properties, @RawRes int propertiesRes) {
        try (InputStreamReader reader = new InputStreamReader(context.getResources().openRawResource(propertiesRes), "UTF-8")) {
            properties.load(reader);
        } catch (IOException e) {
            logger.w(e);
        }
    }

    private void readLoggers(Properties properties, String loggerProperty) {
        String[] appenders = loggerProperty.split(",");
        Map<String, Map<String, String>> appenderMap = createAppenderMap(appenders);
        readProperties(properties, appenderMap);
        createLoggers(appenderMap);
    }

    private void addConfigProperty(Properties properties, String property) {
        String value;
        value = properties.getProperty(property);
        if (value != null) {
            configMap.put(property.substring(LOGGER_CONFIG_PREFIX.length() + 1), value);
        }
    }

    @NonNull
    private Map<String, Map<String, String>> createAppenderMap(String[] appenders) {
        Map<String, Map<String, String>> appenderMap = new HashMap<>();
        for (String appender : appenders) {
            appender = appender.trim();
            checkLoggerName(appender);
            appenderMap.put(appender, new HashMap<>());
        }
        return appenderMap;
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

    private void checkLoggerName(String appenderId) {
        if (TextUtils.isEmpty(appenderId)) {
            throw new IllegalArgumentException("You must specified appender name");
        }
        if (RESERVED_PROPERTIES.contains(LOGGER_CONFIG_PREFIX + "." + appenderId)) {
            throw new IllegalArgumentException("Appender cannot be named as " + appenderId);
        }
    }

    private void createLoggers(Map<String, Map<String, String>> appenderMap) {
        for (Map.Entry<String, Map<String, String>> entry : appenderMap.entrySet()) {
            String clazz = entry.getValue().get("");
            entry.getValue().remove("");
            addLogger(clazz, entry.getKey(), entry.getValue());
        }
    }

    private void addLogger(String appenderClass, String appenderId, Map<String, String> configMap) {
        //noinspection TryWithIdenticalCatches
        try {
            Class clazz = Class.forName(appenderClass);
            Appender appender = (Appender) clazz.newInstance();
            appender.setAppenderId(appenderId);
            if (appender instanceof ConfigurableAppender){
                ((ConfigurableAppender) appender).getConfig().read(configMap);
            }
            loggerMap.put(appenderId, Logger.createWith(appender));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Appender class '" + appenderClass + "' not found");
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(appenderClass + " must have public 0 args constructor");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(appenderClass + " must have public 0 args constructor");
        }
    }


}
