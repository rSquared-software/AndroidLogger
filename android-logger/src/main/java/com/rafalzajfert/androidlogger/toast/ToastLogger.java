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

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.rafalzajfert.androidlogger.BaseLoggerConfig;
import com.rafalzajfert.androidlogger.Configurable;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;

/**
 * {@link Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.5 (26/04/2015)
 */
@SuppressWarnings("unused")
public class ToastLogger extends Logger implements Configurable<ToastLoggerConfig> {

    private ToastLoggerConfig config = new ToastLoggerConfig();

    private Context context;

    public ToastLogger(Context context) {
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public ToastLoggerConfig getConfig() {
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfig(@NonNull ToastLoggerConfig config) {
        this.config = config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void print(Level level, String message) {
        Toast.makeText(context, getTag(level) + PARAM_SPACE + message, config.getDuration()).show();
    }
}
