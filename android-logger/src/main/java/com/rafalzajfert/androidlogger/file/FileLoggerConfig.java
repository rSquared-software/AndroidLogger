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
