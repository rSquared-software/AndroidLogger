package software.rsquared.androidlogger;

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
	protected void append(Level level, Tag tag, Object message, Throwable throwable) {
		if (isLevelAllowed(level)) {
			appender.append(level, tag, message, throwable);
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
		append(INFO, null, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Tag tag, Object message) {
		append(INFO, tag, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void iF(String message, Object... args) {
		append(INFO, null, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void iF(Tag tag, String message, Object... args) {
		append(INFO, tag, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Object... message) {
		append(INFO, null, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Tag tag, Object... message) {
		append(INFO, tag, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Throwable th) {
		append(INFO, null, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Tag tag, Throwable th) {
		append(INFO, tag, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Object message, Throwable th) {
		append(INFO, null, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void i(Tag tag, Object message, Throwable th) {
		append(INFO, tag, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Object message) {
		append(ERROR, null, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Tag tag, Object message) {
		append(ERROR, tag, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eF(String message, Object... args) {
		append(ERROR, null, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eF(Tag tag, String message, Object... args) {
		append(ERROR, tag, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Object... message) {
		append(ERROR, null, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Tag tag, Object... message) {
		append(ERROR, tag, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Throwable th) {
		append(ERROR, null, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Tag tag, Throwable th) {
		append(ERROR, tag, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Object message, Throwable th) {
		append(ERROR, null, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void e(Tag tag, Object message, Throwable th) {
		append(ERROR, tag, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Object message) {
		append(DEBUG, null, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Tag tag, Object message) {
		append(DEBUG, tag, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dF(String message, Object... args) {
		append(DEBUG, null, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dF(Tag tag, String message, Object... args) {
		append(DEBUG, tag, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Object... message) {
		append(DEBUG, null, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Tag tag, Object... message) {
		append(DEBUG, tag, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Throwable th) {
		append(DEBUG, null, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Tag tag, Throwable th) {
		append(DEBUG, tag, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Object message, Throwable th) {
		append(DEBUG, null, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void d(Tag tag, Object message, Throwable th) {
		append(DEBUG, tag, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Object message) {
		append(VERBOSE, null, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Tag tag, Object message) {
		append(VERBOSE, tag, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vF(String message, Object... args) {
		append(VERBOSE, null, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vF(Tag tag, String message, Object... args) {
		append(VERBOSE, tag, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Object... message) {
		append(VERBOSE, null, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Tag tag, Object... message) {
		append(VERBOSE, tag, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Throwable th) {
		append(VERBOSE, null, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Tag tag, Throwable th) {
		append(VERBOSE, tag, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Object message, Throwable th) {
		append(VERBOSE, null, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void v(Tag tag, Object message, Throwable th) {
		append(VERBOSE, tag, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Object message) {
		append(WARNING, null, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Tag tag, Object message) {
		append(WARNING, tag, message, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void wF(String message, Object... args) {
		append(WARNING, null, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void wF(Tag tag, String message, Object... args) {
		append(WARNING, tag, String.format(message, args), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Object... message) {
		append(WARNING, null, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Tag tag, Object... message) {
		append(WARNING, tag, LoggerUtils.array2String(getSeparator(), message), null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Throwable th) {
		append(WARNING, null, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Tag tag, Throwable th) {
		append(WARNING, tag, null, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Object message, Throwable th) {
		append(WARNING, null, message, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void w(Tag tag, Object message, Throwable th) {
		append(WARNING, tag, message, th);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void t() {
		d("At " + Logger.FULL_CLASS_NAME + "." + Logger.METHOD_NAME + Logger.CODE_LINE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void t(Tag tag) {
		d(tag, "At " + Logger.FULL_CLASS_NAME + "." + Logger.METHOD_NAME + Logger.CODE_LINE);
	}
}
