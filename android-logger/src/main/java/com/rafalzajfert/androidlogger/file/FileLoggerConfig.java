package com.rafalzajfert.androidlogger.file;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;
import com.rafalzajfert.androidlogger.Level;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class FileLoggerConfig extends BaseLoggerConfig<FileLoggerConfig>{
    File logFile = null;
    boolean writeTimeEnabled = true;
    String timeFormat = "dd/MM/yyyy HH:mm:ss:SSS ";
    SimpleDateFormat format = new SimpleDateFormat(timeFormat, Locale.getDefault());


    public File getLogFile() {
        return logFile;
    }

    /**
     * Set log file to write messages<br>
     * <br>
     * <b>Note:</b> if you want save log file on external storage you have to
     * add to your Manifest permission:<br>
     * <code>&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;</code>
     */
    public FileLoggerConfig setLogFile(File logFile) {
        this.logFile = logFile;
        return this;
    }

    /**
     * Check that writing current time is enabled or disabled
     */
    public boolean isWriteTimeEnabled() {
        return writeTimeEnabled;
    }

    /**
     * Enable or disable writing current time with log message
     *
     * @see {@link #setTimeFormat(String)}
     */
    public FileLoggerConfig setWriteTimeEnabled(boolean enabled) {
        this.writeTimeEnabled = enabled;
        return this;
    }

    /**
     * Get pattern used to format current time in logs
     */
    public String getTimeFormat() {
        return timeFormat;
    }

    /**
     * Set pattern to format current time in logs
     *
     * @see {@link java.text.SimpleDateFormat}
     */
    public FileLoggerConfig setTimeFormat(String format) {
        this.timeFormat = format;
        this.format = new SimpleDateFormat(timeFormat, Locale.getDefault());
        return this;
    }
}
