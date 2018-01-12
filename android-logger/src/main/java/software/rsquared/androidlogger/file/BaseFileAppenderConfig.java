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
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import software.rsquared.androidlogger.AppenderConfig;

/**
 * @author Rafal Zajfert
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseFileAppenderConfig<E extends BaseFileAppenderConfig> extends AppenderConfig<E> {
	public static final String DATE_PATTERN = "%1$td_%1$tm_%1$tY";
	public static final String DATE = "$Date$";

	private File logFile = null;

	@NonNull
	private String datePattern = DATE_PATTERN;

	private String logFilePath;

	BaseFileAppenderConfig() {
	}

	/**
	 * Returns log file
	 */
	public File getLogFile() {
		return logFile;
	}

	/**
	 * Set log file to write messages
	 */
	public E setLogFile(@Nullable File logFile, boolean overwritePath) {
		if (logFile == null) {
			this.logFile = null;
			if (overwritePath) {
				this.logFilePath = null;
			}
		} else {
			if (overwritePath) {
				this.logFilePath = logFile.getAbsolutePath();
				if (logFile.getAbsolutePath().contains(DATE)) {
					logFile = new File(invalidatePath(logFile.getAbsolutePath()));
				}
			}
			this.logFile = logFile;

		}
		return (E) this;
	}

	/**
	 * Set log file to write messages
	 */
	public E setLogFile(@Nullable File logFile) {
		return setLogFile(logFile, true);
	}

	/**
	 * Set log file to write messages<br><br>
	 * <b>Note:</b> If file path contains '$Date$' it will be replaced with current time<br>
	 * <b>Note:</b> Current time can be formatted with {@link #setDatePattern(String)} method
	 */
	public E setLogFile(@Nullable String path) {
		if (TextUtils.isEmpty(path)) {
			return setLogFile(null, true);
		}
		this.logFilePath = path;
		return setLogFile(new File(invalidatePath(path)), false);
	}

	/**
	 * Set log file to write messages, path must be relative to external storage<br><br>
	 * <b>Note:</b> If file path contains '$Date$' it will be replaced with current time<br>
	 * <b>Note:</b> Current time can be formatted with {@link #setDatePattern(String)} method
	 */
	public E setExternalLogFile(@Nullable String path) {
		if (TextUtils.isEmpty(path)) {
			return setLogFile(null, true);
		}

		return setLogFile(new File(Environment.getExternalStorageDirectory(), invalidatePath(path)), true);
	}

	private String invalidatePath(@NonNull String path) {
		return path.replace(DATE, String.format(datePattern, Calendar.getInstance()));
	}

	/**
	 * Pattern used to format date with {@link SimpleDateFormat}
	 */
	@NonNull
	public String getDatePattern() {
		return datePattern;
	}

	/**
	 * Pattern used to format date with {@link SimpleDateFormat}. <p>Default: <code>{@value #DATE_PATTERN}</code>
	 */
	public E setDatePattern(@NonNull String datePattern) {
		this.datePattern = datePattern;
		setLogFile(logFilePath);
		return (E) this;
	}

	@Override
	protected void read(@NonNull Map<String, String> config) {
		super.read(config);

		if (config.containsKey("externalFile")) {
			//noinspection ResourceType
			setExternalLogFile(config.get("externalFile"));
		}

		if (config.containsKey("datePattern")) {
			//noinspection ResourceType
			setDatePattern(config.get("datePattern"));
		}
	}
}
