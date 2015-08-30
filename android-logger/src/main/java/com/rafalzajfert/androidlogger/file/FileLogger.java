package com.rafalzajfert.androidlogger.file;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that save log messages in the
 * file<br>
 * default file is saved in application files directory
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 * @see android.content.Context#getFilesDir()
 */
@SuppressWarnings("unused")
public class FileLogger extends Logger {

    private static final String DEFAULT_PATH = "log.txt";
    private final FileLoggerConfig config = new FileLoggerConfig();
    private Context context;

    public FileLogger(Context context) {
        this.context = context;
    }

    /**
     * Returns Logger configuration
     */
    public FileLoggerConfig config() {
        return this.config;
    }

    /**
     * Get {@link java.io.File} where log messages are saved
     */
    public File getLogFile() {
        return config.logFile == null ? getDefaultFile() : config.logFile;
    }

    private File getDefaultFile() {
        return new File(context.getFilesDir(), DEFAULT_PATH);
    }

    @Override
    protected String getTag() {
        if (config.tag == null) {
            return super.getTag();
        } else {
            return formatTag(config.tag);
        }
    }

    @Override
    protected boolean isLevelAllowed(@NonNull Level level) {
        return config.isEnabled() && config.isLevelAllowed(level);
    }

    @Override
    protected void print(Level level, String tag, String message) {
        String type = "";
        switch (level) {
            case ERROR:
                type = "E";
                break;
            case INFO:
                type = "I";
                break;
            case DEBUG:
                type = "D";
                break;
            case VERBOSE:
                type = "V";
                break;
            case WARNING:
                type = "W";
                break;
        }
        appendFile(type, tag, message);
    }

    private void appendFile(String type, String tag, String message) {
        RandomAccessFile file = null;
        try {
            checkLogFile();
            file = new RandomAccessFile(getLogFile(), "rw");
            String string = type + " " + getTime() + " " + tag + " " + message + "\r\n";
            file.seek(file.length());
            file.writeChars(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                Log.e(FileLogger.class.getSimpleName(), "Failed to close file", e);
            }
        }
    }

    public void clearLogFile() {
        RandomAccessFile file = null;
        try {
            checkLogFile();
            file = new RandomAccessFile(getLogFile(), "rw");
            file.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                Log.e(FileLogger.class.getSimpleName(), "Cannot create Log file", e);
            }
        }
    }

    private String getTime() {
        return config.writeTimeEnabled ? config.format.format(new Date()) + "" : "";
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void checkLogFile() {
        if (config.logFile == null) {
            config.logFile = getDefaultFile();
        }
        if (!config.logFile.exists()) {
            try {
                config.logFile.createNewFile();
            } catch (IOException e) {
                Log.e(FileLogger.class.getSimpleName(), "Cannot create Log file", e);
            }
        }
    }
}
