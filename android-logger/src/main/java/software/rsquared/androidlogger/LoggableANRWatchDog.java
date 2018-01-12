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

import software.rsquared.androidlogger.logcat.LogcatAppender;

/**
 * LoggableANRWatchDog class<p>
 * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a>
 *
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public class LoggableANRWatchDog extends com.github.anrwatchdog.ANRWatchDog {

    private final AppenderLogger logger = new AppenderLogger(new LogcatAppender());

    private ANRListener customListener;
    private boolean preventCrash = false;

    public LoggableANRWatchDog() {
        super();
    }

    public LoggableANRWatchDog(int timeoutInterval) {
        super(timeoutInterval);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggableANRWatchDog setANRListener(final ANRListener listener) {
        customListener = listener;
        return this;
    }

    /**
     * prevent app crash in the case that an ANR is detected.<p>
     * <b>NOTE:</b> This is experimental future and may not working correctly
     */
    public LoggableANRWatchDog preventCrash(boolean preventCrash) {
        this.preventCrash = preventCrash;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggableANRWatchDog setInterruptionListener(InterruptionListener listener) {
        super.setInterruptionListener(listener);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggableANRWatchDog setReportThreadNamePrefix(String prefix) {
        super.setReportThreadNamePrefix(prefix);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoggableANRWatchDog setReportMainThreadOnly() {
        super.setReportMainThreadOnly();
        return this;
    }

    /**
     * use {@link LoggerConfig#runANRWatchDog(LoggableANRWatchDog)} instead
     */
    @Override
    public synchronized void start() {
        logger.w("=======================================");
        logger.w("LoggableANRWatchDog is running. Please use this carefully because the watchdog will prevent the debugger with hanging execution at breakpoints or exceptions (it will detect the debugging pause as an ANR).");
        logger.w("=======================================");
        super.setANRListener(error -> {
			Logger.error(error);
			if (customListener != null) {
				try {
					customListener.onAppNotResponding(error);
				} catch (Error e) {
					if (!preventCrash) {
						throw e;
					}
				}
			} else {
				if (!preventCrash) {
					throw error;
				}
			}
		});

        super.start();
    }
}
