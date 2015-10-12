/*
 * Copyright 2015 Rafal Zajfert
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

package com.rafalzajfert.androidlogger.file;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class FileLoggerConfig extends BaseLoggerConfig<FileLoggerConfig> {
    public static final String DATE_PATTERN = "dd_MM_yyyy";
    public static final String PARAM_DATE = "$Date";
    private File logFile = null;
    @NonNull
    private String datePattern = DATE_PATTERN;
    @NonNull
    private SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
    private String logFilePath;

    /**
     * Returns log file
     */
    public File getLogFile() {
        return logFile;
    }

    /**
     * Set log file to write messages
     */
    public FileLoggerConfig setLogFile(File logFile) {
        this.logFile = logFile;
        this.logFilePath = this.logFile.getAbsolutePath();
        return this;
    }

    /**
     * Set log file to write messages<br><br>
     * <b>Note:</b> If file path contains '$Date' it will be replaced with current time<br>
     * <b>Note:</b> Current time can be formatted with {@link #setDatePattern(String)} or {@link #setDateFormat(SimpleDateFormat)} method
     */
    public FileLoggerConfig setLogFile(String path) {
        return setLogFile(new File(invalidatePath(path)));
    }

    /**
     * Set log file to write messages, path must be relative to external storage<br><br>
     * <b>Note:</b> If file path contains '$Date' it will be replaced with current time<br>
     * <b>Note:</b> Current time can be formatted with {@link #setDatePattern(String)} or {@link #setDateFormat(SimpleDateFormat)} method
     */
    public FileLoggerConfig setExternalLogFile(String path) {
        return setLogFile(new File(Environment.getExternalStorageDirectory(), invalidatePath(path)));
    }

    private String invalidatePath(String path) {
        return path.replace(PARAM_DATE, dateFormat.format(new Date()));
    }

    @Override
    protected void read(@NonNull Map<String, String> config) {
        super.read(config);

        if (config.containsKey("externalFile")) {
            //noinspection ResourceType
            setExternalLogFile(config.get("externalFile"));
        }

        if (config.containsKey("datePattern")) {
            //noinspection ResourceType
            setDatePattern(config.get("datePattern"));
        }
    }

    /**
     * Pattern used to format date with {@link SimpleDateFormat}
     */
    @NonNull
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * Pattern used to format date with {@link SimpleDateFormat}. <br/>Default: <code>{@value #DATE_PATTERN}</code>
     * <br/><br/>
     * <b>Note:</b> if you changed DateFormat with {@link #setDateFormat(SimpleDateFormat)} before call this method then it will be overwritten
     */
    public FileLoggerConfig setDatePattern(@NonNull String datePattern) {
        this.datePattern = datePattern;
        this.dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
        setLogFile(logFilePath);
        return this;
    }

    /**
     * {@link SimpleDateFormat}  used to format log time in the log tag and log message
     */
    @NonNull
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * {@link SimpleDateFormat}  used to format log time in the log tag and log message.<br/>Default: <code>{@value #DATE_PATTERN}</code>
     * <br/><br/>
     * <b>Note:</b> If you change this parameter then {@link #datePattern} may be unused
     */
    public FileLoggerConfig setDateFormat(@NonNull SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
        setLogFile(logFilePath);
        return this;
    }
}
