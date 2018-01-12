package software.rsquared.androidlogger;

/**
 * @author Rafał Zajfert
 */
abstract class BaseLogger {


	/**
	 * Send an {@link Level#INFO INFO} message<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#info(Object)} method instead<p>
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
	public abstract void i(Object message);

	/**
	 * Send an {@link Level#INFO INFO} message formatted with args objects<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#info(Object)} method instead<p>
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
	public abstract void iF(String message, Object... args);

	/**
	 * Send an {@link Level#INFO INFO} message created
	 * with multiple part<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#info(Object...)} method instead<p>
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
	public abstract void i(Object... message);

	/**
	 * Send an {@link Level#INFO INFO} log of
	 * {@link Throwable}<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#info(Throwable)} method instead
	 */
	public abstract void i(Throwable th);

	/**
	 * Send an {@link Level#INFO INFO} message with
	 * {@link Throwable} log<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#info(Object, Throwable)} method instead<p>
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
	public abstract void i(Object message, Throwable th);

	/**
	 * Send an {@link Level#ERROR ERROR} message<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#error(Object)} method instead<p>
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
	public abstract void e(Object message);

	/**
	 * Send an {@link Level#ERROR ERROR} message formatted with args objects<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#error(Object)} method instead<p>
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
	public abstract void eF(String message, Object... args);

	/**
	 * Send an {@link Level#ERROR ERROR} message created
	 * with multiple part<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#error(Object...)} method instead<p>
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
	public abstract void e(Object... message);

	/**
	 * Send an {@link Level#ERROR ERROR} log of
	 * {@link Throwable}<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#error(Throwable)} method instead
	 */
	public abstract void e(Throwable th);

	/**
	 * Send an {@link Level#ERROR ERROR} message with
	 * {@link Throwable} log<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#error(Object, Throwable)} method instead<p>
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
	public abstract void e(Object message, Throwable th);

	/**
	 * Send an {@link Level#DEBUG DEBUG} message<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#debug(Object)} method instead<p>
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
	public abstract void d(Object message);

	/**
	 * Send an {@link Level#DEBUG DEBUG} message formatted with args objects<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#debug(Object)} method instead<p>
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
	public abstract void dF(String message, Object... args);

	/**
	 * Send an {@link Level#DEBUG DEBUG} message created
	 * with multiple part<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#debug(Object...)} method instead<p>
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
	public abstract void d(Object... message);

	/**
	 * Send an {@link Level#DEBUG DEBUG} log of
	 * {@link Throwable}<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#debug(Throwable)} method instead
	 */
	public abstract void d(Throwable th);

	/**
	 * Send an {@link Level#DEBUG DEBUG} message with
	 * {@link Throwable} log<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#debug(Object, Throwable)} method instead<p>
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
	public abstract void d(Object message, Throwable th);

	/**
	 * Send an {@link Level#VERBOSE VERBOSE} message<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#verbose(Object)} method instead<p>
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
	public abstract void v(Object message);

	/**
	 * Send an {@link Level#VERBOSE VERBOSE} message formatted with args objects<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#verbose(Object)} method instead<p>
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
	public abstract void vF(String message, Object... args);

	/**
	 * Send an {@link Level#VERBOSE VRBOSE} message
	 * created with multiple part<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#verbose(Object...)} method instead<p>
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
	public abstract void v(Object... message);

	/**
	 * Send an {@link Level#VERBOSE VERBOSE} log
	 * of {@link Throwable}<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#verbose(Throwable)} method instead
	 */
	public abstract void v(Throwable th);

	/**
	 * Send an {@link Level#VERBOSE VERBOSE} message
	 * with {@link Throwable} log<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#verbose(Object, Throwable)} method instead<p>
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
	public abstract void v(Object message, Throwable th);

	/**
	 * Send an {@link Level#WARNING WARNING} message<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#warning(Object)} method instead<p>
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
	public abstract void w(Object message);

	/**
	 * Send an {@link Level#WARNING WARNING} message formatted with args objects<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#warning(Object)} method instead<p>
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
	public abstract void wF(String message, Object... args);

	/**
	 * Send an {@link Level#WARNING WARNING} message
	 * created with multiple part<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#warning(Object...)} method instead<p>
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
	public abstract void w(Object... message);

	/**
	 * Send an {@link Level#WARNING WARNING} log
	 * of {@link Throwable}<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#warning(Throwable)} method instead
	 */
	public abstract void w(Throwable th);

	/**
	 * Send an {@link Level#WARNING WARNING} message
	 * with {@link Throwable} log<p>
	 * <p><b>Note </b> this method will append log only with this instance of logger. If you want append log with all the appenders please use {@link Logger#warning(Object, Throwable)} method instead<p>
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
	public abstract void w(Object message, Throwable th);


	/**
	 * Send an {@link Level#DEBUG DEBUG} message with information where this method was called
	 */
	public abstract void t();

	protected abstract void append(Level level, Object message, Throwable throwable);
}
