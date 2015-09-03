package com.rafalzajfert.androidlogger;

import com.github.anrwatchdog.ANRError;
import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

/**
 * ANRWatchDog class<br/><br/>
 * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a>
 *
 * @author Rafal Zajfert
 * @version 1.0.7 (16/05/2015)
 */
@SuppressWarnings("unused")
public class ANRWatchDog extends com.github.anrwatchdog.ANRWatchDog {

    private LogcatLogger logger = new LogcatLogger();

    private ANRListener customListener;
    private boolean preventCrash = false;

    public ANRWatchDog() {
        super();
    }

    public ANRWatchDog(int timeoutInterval) {
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
     * prevent app crash in the case that an ANR is detected.<br/><br/>
     * <b>NOTE:</b> This is experimental future and may not working correctly
     */
    public ANRWatchDog preventCrash(boolean preventCrash) {
        this.preventCrash = preventCrash;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ANRWatchDog setInterruptionListener(InterruptionListener listener) {
        super.setInterruptionListener(listener);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ANRWatchDog setReportThreadNamePrefix(String prefix) {
        super.setReportThreadNamePrefix(prefix);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ANRWatchDog setReportMainThreadOnly() {
        super.setReportMainThreadOnly();
        return this;
    }

    /**
     * @deprecated use {@link LoggerConfig#useANRWatchDog(ANRWatchDog)} instead
     */
    @Deprecated
    @Override
    public synchronized void start() {
        logger.w("=======================================");
        logger.w("ANRWatchDog is running. Please use this carefully because the watchdog will prevent the debugger from hanging execution at breakpoints or exceptions (it will detect the debugging pause as an ANR).");
        logger.w("=======================================");
        super.setANRListener(new ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
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
            }
        });

        super.start();
    }
}
