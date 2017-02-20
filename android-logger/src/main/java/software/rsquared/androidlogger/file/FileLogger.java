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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import software.rsquared.androidlogger.ConfigSetter;
import software.rsquared.androidlogger.Logger;

/**
 * {@link Logger Logger} that save log messages in the
 * file<p>
 * default file is saved in root directory of the default external storage with name "logger.log"
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class FileLogger extends BaseFileLogger implements ConfigSetter<FileLoggerConfig> {

    private FileLoggerConfig config = new FileLoggerConfig();

    public FileLogger() {
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
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

}
