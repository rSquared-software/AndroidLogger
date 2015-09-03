package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;

/**
 * Interface that provide config methods
 * @author Rafal Zajfert
 * @version 1.1.0 (03/09/2015)
 */
public interface Configurable<CONFIG extends BaseLoggerConfig> {

    /**
     * Get config for this instance of logger
     */
    @NonNull
    CONFIG getConfig();

    /**
     * Set config for this instance of logger
     */
    void setConfig(@NonNull CONFIG config);
}
