package software.rsquared.androidlogger;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Rafał Zajfert
 */
@SuppressWarnings("unchecked")
public abstract class AppenderConfig<T extends AppenderConfig>  extends Config {


	/**
	 *  log tag
	 */
	@Getter(AccessLevel.PROTECTED)
	@NonNull
	protected String tag = Logger.CODE_LINE;

	protected AppenderConfig() {
	}

	/**
	 * log tag
	 */
	public T setTag(@NonNull String tag){
		this.tag = tag;
		return (T) this;
	}

	/**
	 * {@link Level Level} that should be logged as other {@link Level}.
	 * This is useful on devices that blocked specified levels, e.g. Sony block {@link Level#VERBOSE} and {@link Level#DEBUG}
	 */
	public T overwriteLevel(@NonNull Level oldLevel, @NonNull Level newLevel) {
		this.overwrittenLevels.put(oldLevel, newLevel);
		return (T) this;
	}

	/**
	 * Remove {@link Level Level} overwriting
	 */
	public T removeLevelOverwriting(@NonNull Level level) {
		this.overwrittenLevels.remove(level);
		return (T) this;
	}

	/**
	 * Remove all {@link Level Level} overwrites
	 */
	public T removeAllLevelOverwrites() {
		this.overwrittenLevels.clear();
		return (T) this;
	}

	/**
	 * Minimal level of the message that should be logged
	 */
	public T setLevel(@NonNull Level level) {
		this.level = level;
		return (T) this;
	}

	/**
	 * if true then all stack trace with throwable will be logged
	 */
	public T setLogThrowableWithStackTrace(boolean logThrowableWithStackTrace) {
		this.logThrowableWithStackTrace = logThrowableWithStackTrace;
		return (T) this;
	}

	/**
	 * Separator between message parts
	 */
	public T setSeparator(@NonNull String separator) {
		this.separator = separator;
		return (T) this;
	}

	/**
	 * Separator between message and throwable
	 */
	public T setThrowableSeparator(@NonNull String throwableSeparator) {
		this.throwableSeparator = throwableSeparator;
		return (T) this;
	}
	/**
	 * {@inheritDoc}
	 */
	@CallSuper
	protected void read(@NonNull Map<String, String> config) {
		super.read(config);
		if (config.containsKey("tag")) {
			tag = config.get("tag");
		}
	}
}
