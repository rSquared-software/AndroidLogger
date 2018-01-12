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

package software.rsquared.androidlogger.toast;

import android.content.Context;
import android.widget.Toast;

import software.rsquared.androidlogger.Appender;
import software.rsquared.androidlogger.ConfigurableAppender;
import software.rsquared.androidlogger.Level;
import software.rsquared.androidlogger.Logger;

/**
 * @author Rafal Zajfert
 */
@SuppressWarnings("unused")
public class ToastAppender extends Appender implements ConfigurableAppender<ToastAppenderConfig> {

	private ToastAppenderConfig config;
	private Context context;

	public ToastAppender(Context context) {
		this.context = context;
	}

	@Override
	public ToastAppenderConfig getConfig() {
		if (config == null) {
			config = new ToastAppenderConfig();
		}
		return config;
	}

	@Override
	protected void append(Level level, String tag, String message) {
		Toast.makeText(context, tag + Logger.SPACE + message, config.getDuration()).show();
	}
}
