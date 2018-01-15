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

package software.rsquared.logger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rsquared.logger.sample.R;

import java.io.File;

import software.rsquared.androidlogger.Level;
import software.rsquared.androidlogger.LoggableANRWatchDog;
import software.rsquared.androidlogger.Logger;
import software.rsquared.androidlogger.Tag;
import software.rsquared.androidlogger.file.RollingFileAppender;
import software.rsquared.androidlogger.logcat.LogcatAppender;
import software.rsquared.androidlogger.textview.TextViewAppender;
import software.rsquared.androidlogger.toast.ToastAppender;

public class MainActivity extends Activity {

	private EditText editText;
	private Spinner typeSpinner;
	private TextView logTextView;

	private boolean isLogcatAdded = false;
	private boolean isToastAdded = false;
	private boolean isTextViewAdded = false;
	private Logger logger = Logger.createWith(new LogcatAppender());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.log_text);
		typeSpinner = (Spinner) findViewById(R.id.log_type);
		logTextView = (TextView) findViewById(R.id.log);
		Logger.error("");


		Logger.error(new Tag("test"),"asd");
		MainActivity context = this;
		Logger.getLoggerConfig().fromProperties(context, R.raw.logger);

		LogcatAppender fileAppender = new LogcatAppender();
		LogcatAppender toastAppender = new LogcatAppender();

		Logger.getLoggerConfig()
				.removeAppender(fileAppender)
				.addAppender(toastAppender)
				.enableDefaultAppender(false)
				.overwriteLevel(Level.DEBUG, Level.INFO)
				.removeLevelOverwriting(Level.VERBOSE)
				.catchUncaughtExceptions()
				.runANRWatchDog(new LoggableANRWatchDog(2000))
				.setDefaultTag(Logger.CODE_LINE)
				.setLevel(Level.DEBUG)
				.setLogThrowableWithStackTrace(true)
				.setSeparator(" | ")
				.setThrowableSeparator(Logger.NEW_LINE)
				.setTimePattern("%1$tH:%1$tM");








//        Logger.error("asd");
//        Logger.getLoggerConfig().overwriteLevel(Level.ERROR, Level.WARNING);
//        Logger.error(getString(R.string.lorem_ipsum));
//
//        Logger.error("[{ \"title\": \"Example Schema\", \"type\": \"object\", \"properties\": { \"firstName\": { \"type\": \"string\" }, \"lastName\": { \"type\": \"string\" }, \"age\": { \"description\": \"Age in years\", \"type\": \"integer\", \"minimum\": 0 } }, \"required\": [\"firstName\", \"lastName\"] },{ \"title\": \"Example Schema\", \"type\": \"object\", \"properties\": { \"firstName\": { \"type\": \"string\" }, \"lastName\": { \"type\": \"string\" }, \"age\": { \"description\": \"Age in years\", \"type\": \"integer\", \"minimum\": 0 } }, \"required\": [\"firstName\", \"lastName\"] }]");
//
//        Logger.getLoggerConfig().removeLevelOverwriting(Level.ERROR);

//        LogcatAppenderConfig logcatLoggerConfig = new LogcatAppenderConfig()
//                .setTag(Logger.FULL_CLASS_NAME + " " + Logger.METHOD_NAME);
//
//        _BaseConfig config = new _BaseConfig(R.raw.logger);
//        ((TextViewAppender)config.getLogger("textView")).setTextView(logTextView);
//        Logger.setBaseConfig(config);
////        try {
////            Integer.parseInt("a");
////        } catch (Exception e) {
////            Logger.error(e);
////            return;
////        }
//        Logger.verbose("verbose");
//        Logger.verboseF("formatted %s message: %.2f", "verbose", 1.1f);
//        Logger.debug("debug");
//        Logger.debugF("formatted %s message: %.2f", "debug", 1.1f);
//        Logger.info("info");
//        Logger.infoF("formatted %s message: %.2f", "info", 1.1f);
//        Logger.warning("warning");
//        Logger.warningF("formatted %s message: %.2f", "warning", 1.1f);
//        Logger.error("error");
//        Logger.errorF("formatted %s message: %.2f", "error", 1.1f);
//
//        loggerConfig = new _BaseConfig()
//                .setTag(Logger.CODE_LINE)
//                .useANRWatchDog(new LoggableANRWatchDog(2000).preventCrash(true))
//                .catchUncaughtExceptions()
//                .setSeparator(Logger.SPACE)
//                .setLogThrowableWithStackTrace(true)
//                .setThrowableSeparator(Logger.NEW_LINE)
//                .setLevel(Level.VERBOSE);
//
//        logger.setConfig(logcatLoggerConfig);
//        Logger.setBaseConfig(loggerConfig);
//
//        Logger.error("start");
//        Logger.warningF("formatted message %s: %.0f", "message", 1.1f);
//        Logger.error("");
//        String a = null;
//        Logger.error(a);
//        try {
//            Integer.parseInt("a");
//        } catch (Exception e) {
//            Logger.error(e);
//        }
//
//        FileAppender fileLogger = new FileAppender();
//        if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            FileAppenderConfig fileLoggerConfig = new FileAppenderConfig().setLogFile(new File(Environment.getExternalStorageDirectory(), "log.txt"));
//            fileLogger.setConfig(fileLoggerConfig);
//        }
//
//        fileLogger.t();
//
//        Logger.getBaseConfig().removeLogger(_BaseConfig.DEFAULT_LOGGER);
//        logger.e("test", "trace will be printed");
//        logger.t();
//        logger.e("test", "end");
//
//        testFileLogger();
	}

	private void testFileLogger() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				RollingFileAppender appender = new RollingFileAppender();
				appender.getConfig().setMaxFileBackupCount(3)
						.setMaxFileSize(20 * 1024)
						.setExternalLogFile("FileTest/test.txt");
				Logger logger = Logger.createWith(appender);

				long maxSize = 0;
				File b2 = new File(Environment.getExternalStorageDirectory(), "FileTest/test.txt.bac2");
				File f = new File(Environment.getExternalStorageDirectory(), "FileTest");
				while (!b2.exists()) {
					logger.d("loggggg", maxSize);
					maxSize = appender.getLogFile().length();
					MainActivity.this.logger.d("f", "=========================", f, f.exists(), maxSize);
					if (f.exists()) {
						for (String filename : f.list()) {
							MainActivity.this.logger.d("f", filename);
						}
						MainActivity.this.logger.d("f", "=========================");
					}
				}


			}
		}).start();
	}

	public void sendToAll(View v) {
		switch (typeSpinner.getSelectedItemPosition()) {
			case 0:
				Logger.verbose(getText());
				break;
			case 1:
				Logger.debug(getText());
				break;
			case 2:
				Logger.info(getText());
				break;
			case 3:
				Logger.warning(getText());
				break;
			case 4:
				Logger.error(getText());
				break;
			default:
				break;
		}
	}

	public void sendToLogcat(View v) {
		if (isLogcatAdded) {
			Logger.getLoggerConfig().removeAppender("logcat");
			((Button) v).setText("Add LogcatAppender");
			logger.d("logcat disabled");
		} else {
			Logger.getLoggerConfig().addAppender("logcat", new LogcatAppender());
			((Button) v).setText("Remove LogcatAppender");
			logger.d("logcat enabled");
		}
		isLogcatAdded = !isLogcatAdded;
	}

	public void sendToToast(View v) {
		if (isToastAdded) {
			Logger.getLoggerConfig().removeAppender("toast");
			((Button) v).setText("Add ToastAppender");
			logger.d("toast disabled");
		} else {
			Logger.getLoggerConfig().addAppender("toast", new ToastAppender(getApplicationContext()));
			((Button) v).setText("Remove ToastAppender");
			logger.d("toast enabled");
		}
		isToastAdded = !isToastAdded;
	}

	public void sendTextView(View v) {
		if (isTextViewAdded) {
			Logger.getLoggerConfig().removeAppender("textview");
			((Button) v).setText("Add TextViewAppender");
			logger.d("textView disabled");
		} else {
			Logger.getLoggerConfig().addAppender("textview", new TextViewAppender(logTextView));
			((Button) v).setText("Remove TextViewAppender");
			logger.d("textView enabled");
		}
		isTextViewAdded = !isTextViewAdded;
	}

	private String getText() {
		return editText.getText().toString();
	}
}
