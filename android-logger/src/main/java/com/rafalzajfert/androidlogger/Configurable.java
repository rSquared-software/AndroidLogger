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

package com.rafalzajfert.androidlogger;

import android.support.annotation.NonNull;

/**
 * Interface that provide config methods
 * @author Rafal Zajfert
 * @version 1.1.0 (03/09/2015)
 */
public interface Configurable<CONFIG extends BaseLoggerConfig> {

    /**
     * Get config for this instance of logger
     */
    @NonNull
    CONFIG getConfig();

    /**
     * Set config for this instance of logger
     */
    void setConfig(@NonNull CONFIG config);
}
