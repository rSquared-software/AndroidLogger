package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.rafalzajfert.androidlogger.Level.DEBUG;
import static com.rafalzajfert.androidlogger.Level.ERROR;
import static com.rafalzajfert.androidlogger.Level.INFO;
import static com.rafalzajfert.androidlogger.Level.VERBOSE;
import static com.rafalzajfert.androidlogger.Level.WARNING;

/**
 * @author Rafal Zajfert
 * @version 1.1.0 (29/04/2015)
 */
@SuppressWarnings("unused")
abstract class BaseLogger {
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
    public static final String CLASS_NAME = "$ClassName$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with full name of class (with package) in which the logger will be called
     */
    public static final String FULL_CLASS_NAME = "$FullClassName$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with name of method in which the logger will be called
     */
    public static final String METHOD_NAME = "$MethodName$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with name of file in which the logger will be called
     */
    public static final String FILE_NAME = "$FileName$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with line number in which the logger will be called
     */
    public static final String LINE_NUMBER = "$LineNumber$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with level of the message
     */
    public static final String LEVEL = "$Level$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with short version of the level (e.g. D for the {@link Level#DEBUG DEBUG}) of the message
     */
    public static final String SHORT_LEVEL = "$ShortLevel$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with current time
     * @see LoggerConfig#setDatePattern(String)
     * @see LoggerConfig#setDateFormat(SimpleDateFormat)
     */
    public static final String CURRENT_TIME = "$CurrentTime$";

    /**
     * This parameter can be used in tag pattern or in message to log and it will be replaced with file name and line number.<p>
     * This param allows you to jump to code line via logcat console.<p>
     * Same as: <code>"({@link #FILE_NAME}:{@link #LINE_NUMBER})"</code>
     */
    public static final String CODE_LINE = "$CodeLine$";

    /**
     * Actual configuration for all loggers
     */
    @NonNull
    static LoggerConfig baseConfig = new LoggerConfig();

    /**
     * Configuration of all loggers
     */
    public static void setBaseConfig(@NonNull LoggerConfig config) {
        baseConfig = config;
    }

    /**
     * Configuration of all loggers, this method should not be used for configuring loggers
     */
    @NonNull
    public static Config getBaseConfig() {
        return baseConfig;
    }

    protected static void printToAll(Level level, @Nullable Object message, @Nullable Throwable throwable){
        if (baseConfig.isLevelAllowed(level)) {
            for (Logger logger : baseConfig.getLoggers()) {
                logger.print(level, message, throwable);
            }
        }
    }

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

    /** returns {@link Level} name*/
    protected static String getLevelName(@NonNull Level level){
        return level.name();
    }

    /** returns first letter of the {@link Level} */
    protected static String getLevelShortName(@NonNull Level level){
        return level.name().substring(0, 1);
    }

    protected static String getTime(){
        return baseConfig.getDateFormat().format(new Date());
    }

    /**
     * Send an {@link Level#INFO INFO} message<p>
     * <p>
     * You can also use auto generated values:<p>
     * {@link Logger#CLASS_NAME CLASS_NAME}<p>
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
    public static void info(Object message) {
        printToAll(INFO, message, null);
    }

    /**
     * Send an {@link Level#INFO INFO} message formatted with args objects<p>
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
    public static void infoF(String message, Object... args) {
        printToAll(INFO, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#INFO INFO} message created
     * from multiple part<p>
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
    public static void info(Object... message) {
        printToAll(INFO, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#INFO INFO} log of
     * {@link Throwable}
     */
    public static void info(Throwable th) {
        printToAll(INFO, null, th);
    }

    /**
     * Send an {@link Level#INFO INFO} message with
     * {@link Throwable} log<p>
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
    public static void info(Object message, Throwable th) {
        printToAll(INFO, message, th);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message<p>
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
    public static void error(Object message) {
        printToAll(ERROR, message, null);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message formatted with args objects<p>
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
    public static void errorF(String message, Object... args) {
        printToAll(ERROR, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message created
     * from multiple part<p>
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
    public static void error(Object... message) {
        printToAll(ERROR, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#ERROR ERROR} log of
     * {@link Throwable}
     */
    public static void error(Throwable th) {
        printToAll(ERROR, null, th);
    }

    /**
     * Send an {@link Level#ERROR ERROR} message with
     * {@link Throwable} log<p>
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
    public static void error(Object message, Throwable th) {
        printToAll(ERROR, message, th);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message<p>
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
    public static void debug(Object message) {
        printToAll(DEBUG, message, null);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message formatted with args objects<p>
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
    public static void debugF(String message, Object... args) {
        printToAll(DEBUG, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message created
     * from multiple part<p>
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
    public static void debug(Object... message) {
        printToAll(DEBUG, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} log of
     * {@link Throwable}
     */
    public static void debug(Throwable th) {
        printToAll(DEBUG, null, th);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message with
     * {@link Throwable} log<p>
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
    public static void debug(Object message, Throwable th) {
        printToAll(DEBUG, message, th);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} message<p>
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
    public static void verbose(Object message) {
        printToAll(VERBOSE, message, null);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} message formatted with args objects<p>
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
    public static void verboseF(String message, Object... args) {
        printToAll(VERBOSE, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#VERBOSE VRBOSE} message
     * created from multiple part<p>
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
    public static void verbose(Object... message) {
        printToAll(VERBOSE, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} log
     * of {@link Throwable}
     */
    public static void verbose(Throwable th) {
        printToAll(VERBOSE, null, th);
    }

    /**
     * Send an {@link Level#VERBOSE VERBOSE} message
     * with {@link Throwable} log<p>
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
    public static void verbose(Object message, Throwable th) {
        printToAll(VERBOSE, message, th);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message<p>
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
    public static void warning(Object message) {
        printToAll(WARNING, message, null);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message formatted with args objects<p>
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
    public static void warningF(String message, Object... args) {
        printToAll(WARNING, String.format(message, args), null);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message
     * created from multiple part<p>
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
    public static void warning(Object... message) {
        printToAll(WARNING, LoggerUtils.array2String(baseConfig.getSeparator(), message), null);
    }

    /**
     * Send an {@link Level#WARNING WARNING} log
     * of {@link Throwable}
     */
    public static void warning(Throwable th) {
        printToAll(WARNING, null, th);
    }

    /**
     * Send an {@link Level#WARNING WARNING} message
     * with {@link Throwable} log<p>
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
    public static void warning(Object message, Throwable th) {
        printToAll(WARNING, message, th);
    }

    /**
     * Send an {@link Level#DEBUG DEBUG} message with information where this method was called
     */
    public static void trace() {
        debug("at " + Logger.FULL_CLASS_NAME + "." + Logger.METHOD_NAME + Logger.CODE_LINE);
    }
}
