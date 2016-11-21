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

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;
import com.rafalzajfert.androidlogger.logcat.LogcatLogger;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * {@link Logger Logger} that save log messages in the
 * file<p>
 * default file is saved in root directory of the default external storage with name "logger.log"
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public abstract class BaseFileLogger extends Logger{

    protected static final LogcatLogger logger = new LogcatLogger();

    protected static final String DEFAULT_LOG_FILE = "logger.log";

    public BaseFileLogger() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void print(Level level, String message) {
        try {
            writeToFile(createFileIfNeeded(), getMessage(level, message));
        } catch (IOException e) {
            logger.e(e);
        }
    }

    /**
     * Get message to write
     */
    @NonNull
    protected String getMessage(Level level, String message) {
        return getTag(level) + SPACE + message + NEW_LINE;
    }

    /**
     * Append string to the end of the file
     */
    protected synchronized void writeToFile(File file, String string) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write(string);
        } catch (IOException e) {
            logger.e("Cannot write log to file", e);
        } finally {
            close(writer);
        }
    }

    /**
     * clear log file
     */
    public synchronized void clearLogFile() {
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


    /**
     * return file or create it when does not exist
     */
    @NonNull
    protected File createFileIfNeeded() throws IOException {
        File file = getLogFile();

        if (file.isDirectory()){
            throw new IOException("The Log file cannot be directory");
        }

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()){
            throw new IOException("Cannot create directory of the Log file");
        }

        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Cannot create Log file");
        }

        if (!file.canWrite()) {
            throw new IOException("File is not writable");
        }

        return file;
    }

    /**
     * Get {@link File} where log messages are saved
     */
    @NonNull
    public File getLogFile() {
        BaseFileLoggerConfig config = (BaseFileLoggerConfig) getConfig();
        File file = null;
        if (config != null){
            file = config.getLogFile();
        }
        if (file == null) {
            file = new File(Environment.getExternalStorageDirectory(), DEFAULT_LOG_FILE);
        }
        return file;
    }

    /**
     * Close file stream
     */
    protected void close(@Nullable Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.e("Failed to close file", e);
            }
        }
    }
}
