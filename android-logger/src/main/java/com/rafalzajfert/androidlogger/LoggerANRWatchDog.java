package com.rafalzajfert.androidlogger;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;

/**
 * Created by Rafał on 2015-05-12.
 */
public class LoggerANRWatchDog extends ANRWatchDog {

	private ANRListener customListener;
	private boolean preventCrash = false;

	public LoggerANRWatchDog() {
		super();
	}

	public LoggerANRWatchDog(int timeoutInterval) {
		super(timeoutInterval);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ANRWatchDog setANRListener(final ANRListener listener) {
		customListener = listener;
		return this;
	}

	/**
	 * prevent app crash in the case that an ANR is detected
	 *
	 * @param preventCrash
	 * @return
	 */
	public LoggerANRWatchDog preventCrash(boolean preventCrash) {
		this.preventCrash = preventCrash;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public LoggerANRWatchDog setInterruptionListener(InterruptionListener listener) {
		super.setInterruptionListener(listener);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public LoggerANRWatchDog setReportThreadNamePrefix(String prefix) {
		super.setReportThreadNamePrefix(prefix);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public LoggerANRWatchDog setReportMainThreadOnly() {
		super.setReportMainThreadOnly();
		return this;
	}

	/**
	 * @deprecated use {@link com.rafalzajfert.androidlogger.LoggerConfig#startANRWatchDog(LoggerANRWatchDog)} instead
	 */
	@Deprecated
	@Override
	public synchronized void start() {
		super.setANRListener(new ANRListener() {
			@Override
			public void onAppNotResponding(ANRError error) {
				Logger.error(error);
				if (customListener != null) {
					try {
						customListener.onAppNotResponding(error);
					} catch (Throwable t) {
						if (!preventCrash) {
							throw t;
						}
					}
				} else {
					if (!preventCrash) {
						throw error;
					}
				}
			}
		});

		super.start();
	}
}
