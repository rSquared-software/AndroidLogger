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

package software.rsquared.androidlogger.logcat;

import android.util.Log;

import software.rsquared.androidlogger.Appender;
import software.rsquared.androidlogger.ConfigurableAppender;
import software.rsquared.androidlogger.Level;

/**
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public class LogcatAppender extends Appender implements ConfigurableAppender<LogcatAppenderConfig> {

    private LogcatAppenderConfig config;

    public LogcatAppender() {
    }

    @Override
    public LogcatAppenderConfig getConfig() {
        if (config == null) {
            config = new LogcatAppenderConfig();
        }
        return config;
    }

    @Override
    protected void append(Level level, String tag, String message) {
        switch (level) {
            case ERROR:
                Log.e(tag, message);
                break;
            case INFO:
                Log.i(tag, message);
                break;
            case DEBUG:
                Log.d(tag, message);
                break;
            case VERBOSE:
                Log.v(tag, message);
                break;
            case WARNING:
                Log.w(tag, message);
                break;
        }
    }
}
