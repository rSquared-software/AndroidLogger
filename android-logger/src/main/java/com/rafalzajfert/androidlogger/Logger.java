package com.rafalzajfert.androidlogger;

import android.util.Log;

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
    private final static LoggerConfig globalConfig = new LoggerConfig();
    /**
     * Logger tag used to identify logger. This tag will not be used to write log message
     */
    String loggerTag;

    /**
     * returns global configuration of loggers e.g. if you disable loggers in this configuration then none of the loggers will be enabled
     */
    public static LoggerConfig globalConfig() {
        return globalConfig;
    }

    private static boolean canLog(Level level) {
        return globalConfig.enabled && globalConfig.isLevelAllowed(level);
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
        if (canLog(Level.INFO)) {
            for (Logger logger : globalConfig.getLoggers()) {
                logger.i(message);
            }
        }
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
        info(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#INFO INFO} log of
     * {@link Throwable}
     */
    public static void info(Throwable th) {
        info(Log.getStackTraceString(th));
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
        info(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
        if (canLog(Level.ERROR)) {
            for (Logger logger : globalConfig.getLoggers()) {
                logger.e(message);
            }
        }
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
        error(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#ERROR ERROR} log of
     * {@link Throwable}
     */
    public static void error(Throwable th) {
        error(Log.getStackTraceString(th));
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
        error(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
        if (canLog(Level.DEBUG)) {
            for (Logger logger : globalConfig.getLoggers()) {
                logger.d(message);
            }
        }
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
        debug(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#DEBUG DEBUG} log of
     * {@link Throwable}
     */
    public static void debug(Throwable th) {
        debug(Log.getStackTraceString(th));
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
        debug(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
        if (canLog(Level.VERBOSE)) {
            for (Logger logger : globalConfig.getLoggers()) {
                logger.v(message);
            }
        }
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
        verbose(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#VERBOSE VERBOSE} log
     * of {@link Throwable}
     */
    public static void verbose(Throwable th) {
        verbose(Log.getStackTraceString(th));
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
        verbose(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
        if (canLog(Level.WARNING)) {
            for (Logger logger : globalConfig.getLoggers()) {
                logger.w(message);
            }
        }
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
        warning(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#WARNING WARNING} log
     * of {@link Throwable}
     */
    public static void warning(Throwable th) {
        warning(Log.getStackTraceString(th));
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
        warning(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
    }

    /**
     * @return by the default formatted message.
     */
    protected abstract String getMessage(Object msg);

    /**
     * @return by the default formatted tag.
     */
    protected abstract String getTag();

    /**
     * This method should return true if message with specified {@link Level} can be printed.<br/>
     * If your Logger have not any restriction then this method should return <b>true</b>.
     *
     * @param level current message {@link com.rafalzajfert.androidlogger.Level}
     */
    protected abstract boolean canLogMessage(Level level);

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
    public void i(Object message) {
        if (canLog(Level.INFO) && canLogMessage(Level.INFO)) {
            print(Level.INFO, getMessage(message));
        }
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
    public void i(Object... message) {
        i(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#INFO INFO} log of
     * {@link Throwable}
     */
    public void i(Throwable th) {
        i(Log.getStackTraceString(th));
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
    public void i(Object message, Throwable th) {
        i(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
    public void e(Object message) {
        if (canLog(Level.ERROR) && canLogMessage(Level.ERROR)) {
            print(Level.ERROR, getMessage(message));
        }
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
    public void e(Object... message) {
        e(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#ERROR ERROR} log of
     * {@link Throwable}
     */
    public void e(Throwable th) {
        e(Log.getStackTraceString(th));
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
    public void e(Object message, Throwable th) {
        e(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
    public void d(Object message) {
        if (canLog(Level.DEBUG) && canLogMessage(Level.DEBUG)) {
            print(Level.DEBUG, getMessage(message));
        }
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
    public void d(Object... message) {
        d(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#DEBUG DEBUG} log of
     * {@link Throwable}
     */
    public void d(Throwable th) {
        d(Log.getStackTraceString(th));
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
    public void d(Object message, Throwable th) {
        d(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
    public void v(Object message) {
        if (canLog(Level.VERBOSE) && canLogMessage(Level.VERBOSE)) {
            print(Level.VERBOSE, getMessage(message));
        }
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
    public void v(Object... message) {
        v(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#VERBOSE VERBOSE} log
     * of {@link Throwable}
     */
    public void v(Throwable th) {
        v(Log.getStackTraceString(th));
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
    public void v(Object message, Throwable th) {
        v(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
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
    public void w(Object message) {
        if (canLog(Level.WARNING) && canLogMessage(Level.WARNING)) {
            print(Level.WARNING, getMessage(message));
        }
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
    public void w(Object... message) {
        w(LoggerUtils.array2String(globalConfig.separator, message));
    }

    /**
     * Send an {@link com.rafalzajfert.androidlogger.Level#WARNING WARNING} log
     * of {@link Throwable}
     */
    public void w(Throwable th) {
        w(Log.getStackTraceString(th));
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
    public void w(Object message, Throwable th) {
        w(message + globalConfig.throwableSeparator + Log.getStackTraceString(th));
    }

    private void print(Level level, String message) {
        print(level, getTag(), message);
    }

    protected abstract void print(Level level, String tag, String message);

    /**
     * @param message message to print with logger
     * @deprecated use {@link #print(Level, String, String)} instead.
     */
    @Deprecated
    protected void printError(String message) {
    }

    /**
     * @param message message to print with logger
     * @deprecated use {@link #print(Level, String, String)} instead.
     */
    @Deprecated
    protected void printInfo(String message) {
    }

    /**
     * @param message message to print with logger
     * @deprecated use {@link #print(Level, String, String)} instead.
     */
    @Deprecated
    protected void printDebug(String message) {
    }

    /**
     * @param message message to print with logger
     * @deprecated use {@link #print(Level, String, String)} instead.
     */
    @Deprecated
    protected void printVerbose(String message) {
    }

    /**
     * @param message message to print with logger
     * @deprecated use {@link #print(Level, String, String)} instead.
     */
    @Deprecated
    protected void printWarning(String message) {
    }

}
