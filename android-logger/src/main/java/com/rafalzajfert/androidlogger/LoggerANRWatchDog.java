package com.rafalzajfert.androidlogger;

import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;

/**
 * LoggerANRWatchDog class<br/><br/>
 * For more information about usage see: <a href="https://github.com/SalomonBrys/ANR-WatchDog" >https://github.com/SalomonBrys/ANR-WatchDog</a>
 *
 * @author Rafal Zajfert
 * @version 1.0.7s (16/05/2015)
 */
@SuppressWarnings("unused")
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
