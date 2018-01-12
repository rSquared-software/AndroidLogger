/*
 * Copyright 2017 rSquared s.c. R. Orlik, R. Zajfert
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

package software.rsquared.androidlogger.file;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import software.rsquared.androidlogger.Appender;
import software.rsquared.androidlogger.ConfigurableAppender;
import software.rsquared.androidlogger.Level;
import software.rsquared.androidlogger.Logger;
import software.rsquared.androidlogger.logcat.LogcatAppender;

/**
 * {@link Logger Logger} that save log messages in the
 * file<p>
 * default file is saved in root directory of the default external storage with name "logger.log"
 *
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public abstract class BaseFileAppender<E extends BaseFileAppenderConfig> extends Appender implements ConfigurableAppender<E>{

    protected static final Logger logger = Logger.createWith(new LogcatAppender());

    protected static final String DEFAULT_LOG_FILE = "logger.log";

    public BaseFileAppender() {
    }

    @Override
    protected void append(Level level, String tag, String message) {
        try {
            writeToFile(createFileIfNeeded(), tag + Logger.SPACE + message + Logger.NEW_LINE);
        } catch (IOException e) {
            logger.e(e);
        }
    }

    /**
     * Append string to the end of the file
     */
    protected synchronized void writeToFile(File file, String string) {
        try (FileWriter writer = new FileWriter(file, true)){
            writer.write(string);
        } catch (IOException e) {
            logger.e("Cannot write log to file", e);
        }
    }

    /**
     * clear log file
     */
    public synchronized void clearLogFile() {
        try (RandomAccessFile file = new RandomAccessFile(getLogFile(), "rw");){
            file.setLength(0);
        } catch (IOException e) {
            logger.e("Cannot clear Log file", e);
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
            throw new IOException("Cannot with directory of the Log file");
        }

        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Cannot with Log file");
        }

        if (!file.canWrite()) {
            throw new IOException("File is not writable");
        }

        return file;
    }

    /**
     * Get {@link File} where log messages should be saved
     */
    @NonNull
    public File getLogFile() {
        BaseFileAppenderConfig config = getConfig();
        File file = null;
        if (config != null){
            file = config.getLogFile();
        }
        if (file == null) {
            file = new File(Environment.getExternalStorageDirectory(), DEFAULT_LOG_FILE);
        }
        return file;
    }
}
