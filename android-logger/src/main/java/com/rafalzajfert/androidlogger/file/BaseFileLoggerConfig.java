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
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public abstract class BaseFileLoggerConfig<E extends BaseLoggerConfig<E>> extends BaseLoggerConfig<E> {
    public static final String DATE_PATTERN = "dd_MM_yyyy";
    public static final String DATE = "$Date$";
    private File logFile = null;
    @NonNull
    private String datePattern = DATE_PATTERN;
    @NonNull
    private DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
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
    public E setLogFile(@Nullable File logFile, boolean overwritePath) {
        if (logFile == null){
            this.logFile = null;
            if (overwritePath) {
                this.logFilePath = null;
            }
        }else {
            if (overwritePath) {
                this.logFilePath = logFile.getAbsolutePath();
                if (logFile.getAbsolutePath().contains(DATE)) {
                    logFile = new File(invalidatePath(logFile.getAbsolutePath()));
                }
            }
            this.logFile = logFile;

        }
        //noinspection unchecked
        return (E) this;
    }

    /**
     * Set log file to write messages
     */
    public E setLogFile(@Nullable File logFile) {
        return setLogFile(logFile, true);
    }

    /**
     * Set log file to write messages<br><br>
     * <b>Note:</b> If file path contains '$Date$' it will be replaced with current time<br>
     * <b>Note:</b> Current time can be formatted with {@link #setDatePattern(String)} or {@link #setDateFormat(DateFormat)} method
     */
    public E setLogFile(@Nullable String path) {
        if (TextUtils.isEmpty(path)){
            return setLogFile(null, true);
        }
        this.logFilePath = path;
        return setLogFile(new File(invalidatePath(path)), false);
    }

    /**
     * Set log file to write messages, path must be relative to external storage<br><br>
     * <b>Note:</b> If file path contains '$Date$' it will be replaced with current time<br>
     * <b>Note:</b> Current time can be formatted with {@link #setDatePattern(String)} or {@link #setDateFormat(DateFormat)} method
     */
    public E setExternalLogFile(@Nullable String path) {
        if (TextUtils.isEmpty(path)){
            return setLogFile(null, true);
        }

        return setLogFile(new File(Environment.getExternalStorageDirectory(), invalidatePath(path)), true);
    }

    private String invalidatePath(@NonNull String path) {
        return path.replace(DATE, dateFormat.format(new Date()));
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
     * <b>Note:</b> if you changed DateFormat with {@link #setDateFormat(DateFormat)} before call this method then it will be overwritten
     */
    public E setDatePattern(@NonNull String datePattern) {
        this.datePattern = datePattern;
        return setDateFormat(new SimpleDateFormat(datePattern, Locale.getDefault()));
    }

    /**
     * {@link SimpleDateFormat}  used to format log time in the log tag and log message
     */
    @NonNull
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * {@link DateFormat}  used to format log time in the log tag and log message.<br/>Default: <code>{@value #DATE_PATTERN}</code>
     * <br/><br/>
     * <b>Note:</b> If you change this parameter then {@link #datePattern} may be unused
     */
    public E setDateFormat(@NonNull DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        setLogFile(logFilePath);
        //noinspection unchecked
        return (E) this;
    }
}
