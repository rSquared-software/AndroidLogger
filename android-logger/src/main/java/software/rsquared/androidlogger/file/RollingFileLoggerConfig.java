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
import android.support.annotation.VisibleForTesting;

import java.util.Map;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class RollingFileLoggerConfig extends BaseFileLoggerConfig<RollingFileLoggerConfig> {
    private static int B = 1;
    private static int KB = 1024 * B;
    private static int MB = 1024 * KB;
    private static int GB = 1024 * MB;

    private long maxFileSize = -1;
    private int maxFileBackupCount = 0;

    public long getMaxFileSize() {
        return maxFileSize;
    }

    @VisibleForTesting
    public BaseFileLoggerConfig setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }

    public int getMaxFileBackupCount() {
        return maxFileBackupCount;
    }

    public BaseFileLoggerConfig setMaxFileBackupCount(int maxFileBackupCount) {
        this.maxFileBackupCount = maxFileBackupCount;
        return this;
    }

    @Override
    protected void read(@NonNull Map<String, String> config) {
        super.read(config);
        if (config.containsKey("maxFileSize")) {
            //noinspection ResourceType
            setMaxFileSize(parseFileSize(config.get("maxFileSize")));
        }

        if (config.containsKey("maxFileBackupCount")) {
            //noinspection ResourceType
            setMaxFileBackupCount(Integer.parseInt(config.get("maxFileBackupCount")));
        }
    }

    private long parseFileSize(@NonNull String size) {
        if (size.matches("^\\d$")) {
            return Long.parseLong(size);
        } else if (size.matches("^\\d+(b|B)$")) {
            size = size.substring(0, size.length() - 1);
            return Long.parseLong(size) * B;
        } else if (size.matches("^\\d+((k|K)(b|B))$")) {
            size = size.substring(0, size.length() - 2);
            return Long.parseLong(size) * KB;
        } else if (size.matches("^\\d+((m|M)(b|B))$")) {
            size = size.substring(0, size.length() - 2);
            return Long.parseLong(size) * MB;
        } else if (size.matches("^\\d+((g|G)(b|B))$")) {
            size = size.substring(0, size.length() - 2);
            return Long.parseLong(size) * GB;
        }
        return Long.parseLong(size);
    }
}
