package com.rafalzajfert.androidlogger.file;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;

import java.io.File;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class FileLoggerConfig extends BaseLoggerConfig<FileLoggerConfig>{
    private File logFile = null;

    /**
     * Returns log file
     */
    public File getLogFile() {
        return logFile;
    }

    /**
     * Set log file to write messages<br/>
     * <br/>
     * <b>Note:</b> if you want save log file on external storage you have to
     * add to your Manifest permission:<br/>
     * <code>&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;</code>
     */
    public FileLoggerConfig setLogFile(File logFile) {
        this.logFile = logFile;
        return this;
    }
}
