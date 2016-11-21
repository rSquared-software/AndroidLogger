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

package com.rafalzajfert.androidlogger.toast;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class ToastLoggerConfig extends BaseLoggerConfig<ToastLoggerConfig> {

    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    @Duration
    private int duration = Toast.LENGTH_SHORT;

    /**
     * How long to display the message.  Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public ToastLoggerConfig setDuration(@Duration int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * How long to display the message.  Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    @Duration
    public int getDuration() {
        return this.duration;
    }

    @Override
    protected void read(@NonNull Map<String, String> config) {
        super.read(config);

        if (config.containsKey("duration")) {
            switch (config.get("duration")){
                case "SHORT":
                    setDuration(LENGTH_SHORT);
                    break;
                case "LONG":
                    setDuration(LENGTH_SHORT);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal duration length. Must be one of: SHORT, LONG");
            }
        }
    }
}
