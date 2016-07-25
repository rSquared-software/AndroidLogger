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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rafalzajfert.androidlogger.ConfigSetter;
import com.rafalzajfert.androidlogger.Logger;

import java.io.File;
import java.io.IOException;

/**
 * {@link Logger Logger} that save log messages in the
 * file<br/>
 * default file is saved in root directory of the default external storage with name "logger.log"
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class RollingFileLogger extends BaseFileLogger implements ConfigSetter<RollingFileLoggerConfig> {

    protected RollingFileLoggerConfig config = new RollingFileLoggerConfig();

    public RollingFileLogger() {
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public RollingFileLoggerConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(@NonNull RollingFileLoggerConfig config) {
        this.config = config;
    }

    @Override
    protected synchronized void writeToFile(File file, String string) {
        super.writeToFile(file, string);
        if (file.length() > config.getMaxFileSize() && config.getMaxFileSize() > 0 && config.getMaxFileBackupCount()>0) {
            rollOver(file);
        }
    }

    private void rollOver(File file) {
        int backupsCount = config.getMaxFileBackupCount();
        File f = new File(file.getAbsolutePath() + ".bac" + backupsCount);
        boolean success = true;
        if (f.exists()) {
            success = f.delete();
        }

        for (int i = backupsCount - 1; i > 0 && success; i--) {
            f = new File(file.getAbsolutePath() + ".bac" + i);
            if (f.exists()) {
                success = f.renameTo(new File(file.getAbsolutePath() + ".bac" + (i + 1)));
            }
        }

        if (success) {
            success = file.renameTo(new File(file.getAbsolutePath() + ".bac" + 1));
        }

        if (success){
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            }
        }
    }
}
