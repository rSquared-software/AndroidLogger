/*
 * Copyright 2017 rSquared s.c. R. Orlik, R. Zajfert
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

package software.rsquared.androidlogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Map;

import static software.rsquared.androidlogger.Level.DEBUG;
import static software.rsquared.androidlogger.Level.ERROR;
import static software.rsquared.androidlogger.Level.INFO;
import static software.rsquared.androidlogger.Level.VERBOSE;
import static software.rsquared.androidlogger.Level.WARNING;

/**
 * Logger class
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public abstract class Logger extends BaseLogger {

    private static final int CHUNK_SIZE = 3000;
    private final boolean hasConfig = this instanceof ConfigSetter;
    /**
     * Logger tag used to identify logger. This tag will not be used to write log message
     */
    protected String loggerTag;

    public String getLoggerTag() {
        return loggerTag;
    }

    /**
     * print message, if you want print tag then call {@link #getTag(Level)} method
     */
    protected abstract void print(Level level, String message);

    /**
     * Get config for this instance of logger
     */
    @Nullable
    protected abstract BaseLoggerConfig getConfig();

    protected void init(@NonNull Map<String, String> config) {
        if (getConfig() != null) {
            getConfig().read(config);
        }
    }

    @Nullable
    private BaseLoggerConfig getConfigIfDefined() {
        if (this.getConfig() != null) {
            return this.getConfig();
        }
        return null;
    }

    /**
     * @return formatted tag
     */
    protected String getTag(Level level) {
        BaseLoggerConfig config = getConfigIfDefined();
        if (config == null || config.getTag() == null) {
            return LoggerUtils.formatTag(baseConfig.getTag(), level);
        } else {
            return LoggerUtils.formatTag(config.getTag(), level);
        }
    }

    protected Context getApplicationContext() {
        return LoggerUtils.getApplicationContext();
    }

    /**
     * @return formatted message
     */
    protected String getMessage(Object msg, Level level) {
        if (msg == null) {
            return null;
        } else {
            return LoggerUtils.formatMessage(msg, level);
        }
    }

    /**
     * This method should return true if message with specified {@link Level} can be printed.<p>
     * If your Logger have not any additional restriction to the level then this method should return <b>true</b>.
     *
     * @param level current message {@link Level}
     */
    protected boolean isLevelAllowed(@NonNull Level level) {
        BaseLoggerConfig config = getConfigIfDefined();
        return config == null || config.isLevelAllowed(level);
    }

    void print(Level level, Object message, Throwable throwable) {
        if (baseConfig.overwrittenLevels.containsKey(level)) {
            level = baseConfig.overwrittenLevels.get(level);
        }
        if (baseConfig.isLevelAllowed(level) && isLevelAllowed(level)) {
            String msg = getMessage(message, level);
            StringBuilder builder = new StringBuilder();

            if (msg == null && throwable == null) {
                builder.append("null");
            } else if (TextUtils.isEmpty(msg) && throwable == null) {
                builder.append("[empty log message]");
            } else if (!TextUtils.isEmpty(msg)) {
                builder.append(msg);
            }

            if (throwable != null) {
                if (builder.length() > 0) {
                    builder.append(baseConfig.getThrowableSeparator());
                }
                builder.append(LoggerUtils.throwableToString(throwable, logWithStackTrace()));
            }
            if (builder.length() > 0) {
                String text = builder.toString();
                if (text.length() > CHUNK_SIZE) {
                    for (String line : WordUtils.wrap(text, CHUNK_SIZE)) {
                        print(level, line);
                    }
                } else {
                    print(level, text);
                }
            }
        }
    }

    private boolean logWithStackTrace() {
        BaseLoggerConfig config = getConfigIfDefined();
        Boolean logThrowableWithStackTrace = config == null ? null : config.isLogThrowableWithStackTrace();
        if (config != null && logThrowableWithStackTrace != null) {
            return logThrowableWithStackTrace;
        } else {
            return baseConfig.isLogThrowableWithStackTrace();
        }
    }

    /**
     * Send an {@link Level#INFO INFO} message<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#info(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void i(Object message) {
        print(INFO, message, null);
    }

    /**
     * Send an {@link Level#INFO INFO} message formatted with args objects<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#info(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void iF(String message, Object... args) {
        print(INFO, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#INFO INFO} message created
     * from multiple part<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#info(Object...)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message parts to send
     */
    public void i(Object... message) {
        print(INFO, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#INFO INFO} log of
     * {@link Throwable}<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#info(Throwable)} method instead
     */
    public void i(Throwable th) {
        print(INFO, null, th);
    }

    /**
     * Send an {@link Level#INFO INFO} message with
     * {@link Throwable} log<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#info(Object, Throwable)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void i(Object message, Throwable th) {
        print(INFO, message, th);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#error(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void e(Object message) {
        print(ERROR, message, null);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message formatted with args objects<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#error(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void eF(String message, Object... args) {
        print(ERROR, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message created
     * from multiple part<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#error(Object...)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message parts to send
     */
    public void e(Object... message) {
        print(ERROR, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#ERROR ERROR} log of
     * {@link Throwable}<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#error(Throwable)} method instead
     */
    public void e(Throwable th) {
        print(ERROR, null, th);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message with
     * {@link Throwable} log<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#error(Object, Throwable)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void e(Object message, Throwable th) {
        print(ERROR, message, th);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#debug(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void d(Object message) {
        print(DEBUG, message, null);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message formatted with args objects<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#debug(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void dF(String message, Object... args) {
        print(DEBUG, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message created
     * from multiple part<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#debug(Object...)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message parts to send
     */
    public void d(Object... message) {
        print(DEBUG, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} log of
     * {@link Throwable}<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#debug(Throwable)} method instead
     */
    public void d(Throwable th) {
        print(DEBUG, null, th);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message with
     * {@link Throwable} log<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#debug(Object, Throwable)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void d(Object message, Throwable th) {
        print(DEBUG, message, th);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} message<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#verbose(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void v(Object message) {
        print(VERBOSE, message, null);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} message formatted with args objects<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#verbose(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void vF(String message, Object... args) {
        print(VERBOSE, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#VERBOSE VRBOSE} message
     * created from multiple part<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#verbose(Object...)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message parts to send
     */
    public void v(Object... message) {
        print(VERBOSE, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} log
     * of {@link Throwable}<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#verbose(Throwable)} method instead
     */
    public void v(Throwable th) {
        print(VERBOSE, null, th);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} message
     * with {@link Throwable} log<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#verbose(Object, Throwable)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void v(Object message, Throwable th) {
        print(VERBOSE, message, th);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#warning(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void w(Object message) {
        print(WARNING, message, null);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message formatted with args objects<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#warning(Object)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void wF(String message, Object... args) {
        print(WARNING, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message
     * created from multiple part<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#warning(Object...)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message parts to send
     */
    public void w(Object... message) {
        print(WARNING, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#WARNING WARNING} log
     * of {@link Throwable}<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#warning(Throwable)} method instead
     */
    public void w(Throwable th) {
        print(WARNING, null, th);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message
     * with {@link Throwable} log<p>
     * <p><b>Note </b> this method will print log only with this instance of logger. If you want print log with all the loggers please use {@link Logger#warning(Object, Throwable)} method instead<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME
     * CLASS_NAME}<p>
     * {@link Logger#FULL_CLASS_NAME FULL_CLASS_NAME}<p>
     * {@link Logger#METHOD_NAME METHOD_NAME}<p>
     * {@link Logger#FILE_NAME FILE_NAME}<p>
     * {@link Logger#LINE_NUMBER LINE_NUMBER}<p>
     * {@link Logger#LEVEL LEVEL}<p>
     * {@link Logger#SHORT_LEVEL SHORT_LEVEL}<p>
     * {@link Logger#CURRENT_TIME CURRENT_TIME}<p>
     * {@link Logger#CODE_LINE CODE_LINE}
     *
     * @param message message to send
     */
    public void w(Object message, Throwable th) {
        print(WARNING, message, th);
    }


    /**
     * Send an {@link Level#DEBUG DEBUG} message with information where this method was called
     */
    public void t() {
        d("At " + FULL_CLASS_NAME + "." + METHOD_NAME + CODE_LINE);
    }
}
