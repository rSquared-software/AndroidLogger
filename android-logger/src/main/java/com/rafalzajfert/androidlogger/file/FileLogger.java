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
import com.rafalzajfert.androidlogger.Configurable;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;
import com.rafalzajfert.androidlogger.logcat.LogcatLogger;
import com.rafalzajfert.androidlogger.logcat.LogcatLoggerConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * {@link Logger Logger} that save log messages in the
 * file<br/>
 * default file is saved in root directory of the default external storage with name "logger.log"
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class FileLogger extends Logger implements Configurable<FileLoggerConfig> {

    private static LogcatLogger logger = new LogcatLogger();

    private static final String DEFAULT_FILE = "logger.log";
    private FileLoggerConfig config = new FileLoggerConfig();

    public FileLogger() {
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public FileLoggerConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(@NonNull FileLoggerConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void print(Level level, String message) {
        RandomAccessFile writer = null;
        try {
            File file = createFileIfNeeded();

            if ( file == null )
                return;

            writer = new RandomAccessFile(getLogFile(), "rw");
            String string = getTag(level) + PARAM_SPACE + message + PARAM_NEW_LINE;
            writer.seek(file.length());
            writer.writeChars(string);
        } catch (IOException e) {
            logger.e("Cannot write log to file", e);
        } finally {
            close(writer);
        }
    }

    /**
     * clear log file
     */
    public void clearLogFile() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(getLogFile(), "rw");
            file.setLength(0);
        } catch (IOException e) {
            logger.e("Cannot clear Log file", e);
        } finally {
            close(file);
        }
    }

    private void close(RandomAccessFile file) {
        try {
            if (file != null) {
                file.close();
            }
        } catch (IOException e) {
            logger.e("Failed to close file", e);
        }
    }

    private File createFileIfNeeded() {
        try {
            File file = getLogFile();

            if (!file.exists() && !file.createNewFile()){
                throw new IOException("Cannot create Log file");
            }

            if (!file.canWrite()){
                throw new IOException("File is not writable");
            }

            return file;
        } catch (Exception e) {
            logger.e("Cannot create Log file", e);
            return null;
        }
    }

    /**
     * Get {@link File} where log messages are saved
     */
    @NonNull
    public File getLogFile() {
        File file = config.getLogFile();
        if (file == null) {
            file = new File(Environment.getExternalStorageDirectory(), DEFAULT_FILE);
        }
        return file;
    }
}
