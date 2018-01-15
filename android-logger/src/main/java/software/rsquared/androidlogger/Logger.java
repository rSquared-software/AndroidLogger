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

import android.support.annotation.Nullable;

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
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Logger extends BaseLogger {

	/**
	 * Space: " "
	 */
	public static final String SPACE = " ";

	/**
	 * New Line: "\n"
	 */
	public static final String NEW_LINE = "\n";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with simple name of class in which the logger will be called
	 */
	public static final String CLASS_NAME = "$ClassName$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with full name of class (with package) in which the logger will be called
	 */
	public static final String FULL_CLASS_NAME = "$FullClassName$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with name of method in which the logger will be called
	 */
	public static final String METHOD_NAME = "$MethodName$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with name of file in which the logger will be called
	 */
	public static final String FILE_NAME = "$FileName$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with line number in which the logger will be called
	 */
	public static final String LINE_NUMBER = "$LineNumber$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with level of the message
	 */
	public static final String LEVEL = "$Level$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with short version of the level (e.g. D for the {@link Level#DEBUG DEBUG}) of the message
	 */
	public static final String SHORT_LEVEL = "$ShortLevel$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with current time
	 *
	 * @see LoggerConfig#setTimePattern(String)
	 */
	public static final String CURRENT_TIME = "$CurrentTime$";

	/**
	 * This parameter can be used in appenderId pattern or in message to log and it will be replaced with file name and line number.<p>
	 * This param allows you to jump to code line via logcat console.<p>
	 * Same as: <code>"({@link #FILE_NAME}:{@link #LINE_NUMBER})"</code>
	 */
	public static final String CODE_LINE = "$CodeLine$";

	Logger() {
	}

	/**
	 * Global configuration for all logger appenders
	 */
	public static LoggerConfig getLoggerConfig() {
		return LoggerConfig.getInstance();
	}

	/**
	 * Logger for appender with given value
	 */
	public static Logger getLogger(String appenderId) {
		return getLoggerConfig().getLoggerMap().get(appenderId);
	}

	public static Logger createWith(Appender appender) {
		return new AppenderLogger(appender);
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
		appendToAll(INFO, message, null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void info(Tag tag, Object message) {
		appendToAll(INFO, tag, message, null);
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
		appendToAll(INFO, String.format(message, args), null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void infoF(Tag tag, String message, Object... args) {
		appendToAll(INFO, tag, String.format(message, args), null);
	}

	/**
	 * Send an {@link Level#INFO INFO} message created
	 * with multiple part<p>
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
		appendToAll(INFO, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#INFO INFO} message created
	 * with multiple part<p>
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
	 * @param tag     custom value of the log
	 * @param message message parts to send
	 */
	public static void info(Tag tag, Object... message) {
		appendToAll(INFO, tag, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#INFO INFO} log of
	 * {@link Throwable}
	 */
	public static void info(Throwable th) {
		appendToAll(INFO, null, th);
	}

	/**
	 * Send an {@link Level#INFO INFO} log of
	 * {@link Throwable}
	 *
	 * @param tag custom value of the log
	 */
	public static void info(Tag tag, Throwable th) {
		appendToAll(INFO, tag, null, th);
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
		appendToAll(INFO, message, th);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void info(Tag tag, Object message, Throwable th) {
		appendToAll(INFO, tag, message, th);
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
		appendToAll(ERROR, message, null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void error(Tag tag, Object message) {
		appendToAll(ERROR, tag, message, null);
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
		appendToAll(ERROR, String.format(message, args), null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void errorF(Tag tag, String message, Object... args) {
		appendToAll(ERROR, tag, String.format(message, args), null);
	}

	/**
	 * Send an {@link Level#ERROR ERROR} message created
	 * with multiple part<p>
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
		appendToAll(ERROR, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#ERROR ERROR} message created
	 * with multiple part<p>
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
	 * @param tag     custom value of the log
	 * @param message message parts to send
	 */
	public static void error(Tag tag, Object... message) {
		appendToAll(ERROR, tag, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#ERROR ERROR} log of
	 * {@link Throwable}
	 */
	public static void error(Throwable th) {
		appendToAll(ERROR, null, th);
	}

	/**
	 * Send an {@link Level#ERROR ERROR} log of
	 * {@link Throwable}
	 *
	 * @param tag custom value of the log
	 */
	public static void error(Tag tag, Throwable th) {
		appendToAll(ERROR, tag, null, th);
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
		appendToAll(ERROR, message, th);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void error(Tag tag, Object message, Throwable th) {
		appendToAll(ERROR, tag, message, th);
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
		appendToAll(DEBUG, message, null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void debug(Tag tag, Object message) {
		appendToAll(DEBUG, tag, message, null);
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
		appendToAll(DEBUG, String.format(message, args), null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void debugF(Tag tag, String message, Object... args) {
		appendToAll(DEBUG, tag, String.format(message, args), null);
	}

	/**
	 * Send an {@link Level#DEBUG DEBUG} message created
	 * with multiple part<p>
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
		appendToAll(DEBUG, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#DEBUG DEBUG} message created
	 * with multiple part<p>
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
	 * @param tag     custom value of the log
	 * @param message message parts to send
	 */
	public static void debug(Tag tag, Object... message) {
		appendToAll(DEBUG, tag, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#DEBUG DEBUG} log of
	 * {@link Throwable}
	 */
	public static void debug(Throwable th) {
		appendToAll(DEBUG, null, th);
	}

	/**
	 * Send an {@link Level#DEBUG DEBUG} log of
	 * {@link Throwable}
	 *
	 * @param tag custom value of the log
	 */
	public static void debug(Tag tag, Throwable th) {
		appendToAll(DEBUG, tag, null, th);
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
		appendToAll(DEBUG, message, th);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void debug(Tag tag, Object message, Throwable th) {
		appendToAll(DEBUG, tag, message, th);
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
		appendToAll(VERBOSE, message, null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void verbose(Tag tag, Object message) {
		appendToAll(VERBOSE, tag, message, null);
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
		appendToAll(VERBOSE, String.format(message, args), null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void verboseF(Tag tag, String message, Object... args) {
		appendToAll(VERBOSE, tag, String.format(message, args), null);
	}

	/**
	 * Send an {@link Level#VERBOSE VRBOSE} message
	 * created with multiple part<p>
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
		appendToAll(VERBOSE, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#VERBOSE VRBOSE} message
	 * created with multiple part<p>
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
	 * @param tag     custom value of the log
	 * @param message message parts to send
	 */
	public static void verbose(Tag tag, Object... message) {
		appendToAll(VERBOSE, tag, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#VERBOSE VERBOSE} log
	 * of {@link Throwable}
	 */
	public static void verbose(Throwable th) {
		appendToAll(VERBOSE, null, th);
	}

	/**
	 * Send an {@link Level#VERBOSE VERBOSE} log
	 * of {@link Throwable}
	 *
	 * @param tag custom value of the log
	 */
	public static void verbose(Tag tag, Throwable th) {
		appendToAll(VERBOSE, tag, null, th);
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
		appendToAll(VERBOSE, message, th);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void verbose(Tag tag, Object message, Throwable th) {
		appendToAll(VERBOSE, tag, message, th);
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
		appendToAll(WARNING, message, null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void warning(Tag tag, Object message) {
		appendToAll(WARNING, tag, message, null);
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
		appendToAll(WARNING, String.format(message, args), null);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void warningF(Tag tag, String message, Object... args) {
		appendToAll(WARNING, tag, String.format(message, args), null);
	}

	/**
	 * Send an {@link Level#WARNING WARNING} message
	 * created with multiple part<p>
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
		appendToAll(WARNING, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#WARNING WARNING} message
	 * created with multiple part<p>
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
	 * @param tag     custom value of the log
	 * @param message message parts to send
	 */
	public static void warning(Tag tag, Object... message) {
		appendToAll(WARNING, tag, LoggerUtils.array2String(getLoggerConfig().getSeparator(), message), null);
	}

	/**
	 * Send an {@link Level#WARNING WARNING} log
	 * of {@link Throwable}
	 */
	public static void warning(Throwable th) {
		appendToAll(WARNING, null, th);
	}

	/**
	 * Send an {@link Level#WARNING WARNING} log
	 * of {@link Throwable}
	 *
	 * @param tag custom value of the log
	 */
	public static void warning(Tag tag, Throwable th) {
		appendToAll(WARNING, tag, null, th);
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
		appendToAll(WARNING, message, th);
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
	 * @param tag     custom value of the log
	 * @param message message to send
	 */
	public static void warning(Tag tag, Object message, Throwable th) {
		appendToAll(WARNING, tag, message, th);
	}

	/**
	 * Send an {@link Level#DEBUG DEBUG} message with information where this method was called
	 */
	public static void trace() {
		debug("at " + Logger.FULL_CLASS_NAME + "." + Logger.METHOD_NAME + Logger.CODE_LINE);
	}

	/**
	 * Send an {@link Level#DEBUG DEBUG} message with information where this method was called
	 *
	 * @param tag custom value of the log
	 */
	public static void trace(Tag tag) {
		debug(tag, "at " + Logger.FULL_CLASS_NAME + "." + Logger.METHOD_NAME + Logger.CODE_LINE);
	}

	private static void appendToAll(Level level, @Nullable Object message, @Nullable Throwable throwable) {
		appendToAll(level, null, message, throwable);
	}

	private static void appendToAll(Level level, Tag tag, @Nullable Object message, @Nullable Throwable throwable) {
		LoggerConfig loggerConfig = getLoggerConfig();
		if (loggerConfig.getOverwrittenLevels().containsKey(level)) {
			level = loggerConfig.getOverwrittenLevels().get(level);
		}
		if (loggerConfig.isLevelAllowed(level)) {
			for (Logger logger : loggerConfig.getLoggerMap().values()) {
				logger.append(level, tag, message, throwable);
			}
		}
	}

}
