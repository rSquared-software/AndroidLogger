package software.rsquared.androidlogger;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Calendar;
import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;

import static software.rsquared.androidlogger.Level.DEBUG;
import static software.rsquared.androidlogger.Level.ERROR;
import static software.rsquared.androidlogger.Level.INFO;
import static software.rsquared.androidlogger.Level.VERBOSE;
import static software.rsquared.androidlogger.Level.WARNING;

/**
 * @author Rafał Zajfert
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class AppenderLogger extends Logger {

	@Getter(AccessLevel.PUBLIC)
	private final Appender appender;

	AppenderLogger(Appender appender) {
		this.appender = appender;
	}

	@Override
	protected void append(Level level, Object message, Throwable throwable) {
		if (isLevelAllowed(level)) {
			appender.append(level, message, throwable);
		}
	}

	private String getSeparator() {
		String separator = null;
		if (appender instanceof ConfigurableAppender) {
			Config appenderConfig = ((ConfigurableAppender) appender).getConfig();
			if (appenderConfig != null) {
				separator = appenderConfig.getSeparator();
			}
		}
		if (separator == null){
			separator = LoggerConfig.getInstance().getSeparator();
		}

		return separator;
	}

	private boolean isLevelAllowed(Level level) {
		Config config = LoggerConfig.getInstance();
		Config appenderConfig = null;
		if (appender instanceof ConfigurableAppender){
			appenderConfig = ((ConfigurableAppender) appender).getConfig();
		}

		if (config.getOverwrittenLevels().containsKey(level)) {
			level = config.getOverwrittenLevels().get(level);
		}

		return config.isLevelAllowed(level) && (appenderConfig == null || appenderConfig.isLevelAllowed(level));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Object message) {
		append(INFO, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void iF(String message, Object... args) {
		append(INFO, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Object... message) {
		append(INFO, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Throwable th) {
		append(INFO, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Object message, Throwable th) {
		append(INFO, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Object message) {
		append(ERROR, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eF(String message, Object... args) {
		append(ERROR, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Object... message) {
		append(ERROR, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Throwable th) {
		append(ERROR, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Object message, Throwable th) {
		append(ERROR, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Object message) {
		append(DEBUG, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dF(String message, Object... args) {
		append(DEBUG, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Object... message) {
		append(DEBUG, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Throwable th) {
		append(DEBUG, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Object message, Throwable th) {
		append(DEBUG, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Object message) {
		append(VERBOSE, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vF(String message, Object... args) {
		append(VERBOSE, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Object... message) {
		append(VERBOSE, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Throwable th) {
		append(VERBOSE, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Object message, Throwable th) {
		append(VERBOSE, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Object message) {
		append(WARNING, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void wF(String message, Object... args) {
		append(WARNING, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Object... message) {
		append(WARNING, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Throwable th) {
		append(WARNING, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Object message, Throwable th) {
		append(WARNING, message, th);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void t() {
		d("At " + Logger.FULL_CLASS_NAME + "." + Logger.METHOD_NAME + Logger.CODE_LINE);
	}
}
