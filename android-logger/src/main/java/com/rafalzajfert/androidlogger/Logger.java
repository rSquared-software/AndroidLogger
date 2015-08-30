package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.rafalzajfert.androidlogger.Level.*;

/**
 * Logger class
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public abstract class Logger {

    /**
     * Space: " "
     */
    public static final String SPACE = " ";

    /**
     * New Line: "\n"
     */
    public static final String NEW_LINE = "\n";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with simple name of class in which the logger will be called
     */
    public static final String PARAM_SIMPLE_CLASS_NAME = "$SimpleClassName";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with full name of class in which the logger will be called
     */
    public static final String PARAM_CLASS_NAME = "$ClassName";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with name of method in which the logger will be called
     */
    public static final String PARAM_METHOD_NAME = "$MethodName";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with name of file in which the logger will be called
     */
    public static final String PARAM_FILE_NAME = "$FileName";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with line number in which the logger will be called
     */
    public static final String PARAM_LINE_NUMBER = "$LineNumber";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with level of the message
     */
    public static final String PARAM_LEVEL = "$Level";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with short version of the level (e.g. D for the {@link Level#DEBUG DEBUG}) of the message
     */
    public static final String PARAM_SHORT_LEVEL = "$ShortLevel";

    /**
     * Actual configuration for all loggers
     */
    protected static LoggerConfig config = new LoggerConfig();

    /**
     * Logger tag used to identify logger. This tag will not be used to write log message
     */
    protected String loggerTag;

    /**
     * Configuration of all loggers
     */
    public static void setConfig(@NonNull LoggerConfig config) {
        Logger.config = config;
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#INFO INFO} message<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void info(Object message) {
        printToAll(INFO, message, null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#INFO INFO} message created
     * from multiple part<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message parts to send
     */
    public static void info(Object... message) {
        printToAll(INFO, LoggerUtils.array2String(config.getSeparator(), message), null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#INFO INFO} log of
     * {@link Throwable}
     */
    public static void info(Throwable th) {
        printToAll(INFO, null, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#INFO INFO} message with
     * {@link Throwable} log<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void info(Object message, Throwable th) {
        printToAll(INFO, message, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#ERROR ERROR} message<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void error(Object message) {
        printToAll(ERROR, message, null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#ERROR ERROR} message created
     * from multiple part<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message parts to send
     */
    public static void error(Object... message) {
        printToAll(ERROR, LoggerUtils.array2String(config.getSeparator(), message), null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#ERROR ERROR} log of
     * {@link Throwable}
     */
    public static void error(Throwable th) {
        printToAll(ERROR, null, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#ERROR ERROR} message with
     * {@link Throwable} log<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void error(Object message, Throwable th) {
        printToAll(ERROR, message, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#DEBUG DEBUG} message<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void debug(Object message) {
        printToAll(DEBUG, message, null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#DEBUG DEBUG} message created
     * from multiple part<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message parts to send
     */
    public static void debug(Object... message) {
        printToAll(DEBUG, LoggerUtils.array2String(config.getSeparator(), message), null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#DEBUG DEBUG} log of
     * {@link Throwable}
     */
    public static void debug(Throwable th) {
        printToAll(DEBUG, null, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#DEBUG DEBUG} message with
     * {@link Throwable} log<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void debug(Object message, Throwable th) {
        printToAll(DEBUG, message, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#VERBOSE VERBOSE} message<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void verbose(Object message) {
        printToAll(VERBOSE, message, null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#VERBOSE VRBOSE} message
     * created from multiple part<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message parts to send
     */
    public static void verbose(Object... message) {
        printToAll(VERBOSE, LoggerUtils.array2String(config.getSeparator(), message), null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#VERBOSE VERBOSE} log
     * of {@link Throwable}
     */
    public static void verbose(Throwable th) {
        printToAll(VERBOSE, null, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#VERBOSE VERBOSE} message
     * with {@link Throwable} log<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void verbose(Object message, Throwable th) {
        printToAll(VERBOSE, message, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#WARNING WARNING} message<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void warning(Object message) {
        printToAll(WARNING, message, null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#WARNING WARNING} message
     * created from multiple part<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message parts to send
     */
    public static void warning(Object... message) {
        printToAll(WARNING, LoggerUtils.array2String(config.getSeparator(), message), null);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#WARNING WARNING} log
     * of {@link Throwable}
     */
    public static void warning(Throwable th) {
        printToAll(WARNING, null, th);
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#WARNING WARNING} message
     * with {@link Throwable} log<br>
     * <br>
     * You can also use auto generated values:<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_SIMPLE_CLASS_NAME
     * PARAM_SIMPLE_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_CLASS_NAME PARAM_CLASS_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_METHOD_NAME PARAM_METHOD_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_FILE_NAME PARAM_FILE_NAME}<br>
     * {@link com.rafalzajfert.androidlogger.Logger#PARAM_LINE_NUMBER PARAM_LINE_NUMBER}
     *
     * @param message message to send
     */
    public static void warning(Object message, Throwable th) {
        printToAll(WARNING, message, th);
    }

    /**
     * This method should return true if message with specified {@link Level} can be printed.<br/>
     * If your Logger have not any additional restriction to the level then this method should return <b>true</b>.
     *
     * @param level current message {@link com.rafalzajfert.androidlogger.Level}
     */
    protected boolean isLevelAllowed(@NonNull Level level) {
        BaseLoggerConfig config = getConfig();
        return config == null || config.isLevelAllowed(level);
    }

    /**
     * Get local logger config or null if not implemented
     */
    @Nullable
    protected abstract BaseLoggerConfig getConfig();

    private static void printToAll(Level level, Object message, Throwable throwable){
        if (config.isLevelAllowed(level)) {
            for (Logger logger : config.getLoggers()) {
                if (logger.isLevelAllowed(level)) {
                    logger.print(Level.INFO, logger.getMessage(message), throwable);
                }
            }
        }
    }

    private void print(Level level, String message, Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        if (message!=null){
            builder.append(message).append(Logger.config.getThrowableSeparator());
        }
        if (throwable!=null) {
            BaseLoggerConfig config = getConfig();
            if (config == null || config.isLogThrowableWithStackTrace() == null) {
                builder.append(LoggerUtils.throwableToString(throwable, Logger.config));
            } else {
                builder.append(LoggerUtils.throwableToString(throwable, config));
            }
        }
        print(level, builder.toString());
    }

    protected abstract void print(Level level, String message);

    protected static String getSimpleClassName() {
        return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.SIMPLE_CLASS_NAME);
    }

    protected static String getFullClassName() {
        return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FULL_CLASS_NAME);
    }

    protected static String getFileName() {
        return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.FILE_NAME);
    }

    protected static String getMethodName() {
        return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.METHOD_NAME);
    }

    protected static String getLineNumber() {
        return LoggerUtils.getStackTraceField(LoggerUtils.StackTraceField.LINE_NUMBER);
    }

    protected static String getLevelName(@NonNull Level level){
        return level.name();
    }

    protected static String getLevelShortName(@NonNull Level level){
        return level.name().substring(0, 1);
    }

    /**
     * @return formatted tag
     */
    protected String getTag(Level level) {
        BaseLoggerConfig config = getConfig();
        if (config == null || config.getTag() == null) {
            return LoggerUtils.formatTag(Logger.config.getTag(), level);
        }else {
            return LoggerUtils.formatTag(config.getTag(), level);
        }
    }

    /**
     * @return formatted message
     */
    protected String getMessage(Object msg) {
        if (msg == null){
            return null;
        }else{
            return LoggerUtils.formatMessage(msg);
        }

    }
}
