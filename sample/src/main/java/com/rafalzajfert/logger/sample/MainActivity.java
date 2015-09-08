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

package com.rafalzajfert.logger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rafalzajfert.androidlogger.LoggableANRWatchDog;
import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;
import com.rafalzajfert.androidlogger.LoggerConfig;
import com.rafalzajfert.androidlogger.logcat.LogcatLogger;
import com.rafalzajfert.androidlogger.logcat.LogcatLoggerConfig;
import com.rafalzajfert.androidlogger.textview.TextViewLogger;
import com.rafalzajfert.androidlogger.toast.ToastLogger;

public class MainActivity extends Activity {

    private EditText editText;
    private Spinner typeSpinner;
    private TextView logTextView;

    private boolean isLogcatAdded = false;
    private boolean isToastAdded = false;
    private boolean isTextViewAdded = false;
    private LogcatLogger logger = new LogcatLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.log_text);
        typeSpinner = (Spinner) findViewById(R.id.log_type);
        logTextView = (TextView) findViewById(R.id.log);

        LogcatLoggerConfig logcatLoggerConfig = new LogcatLoggerConfig()
                .setTag(Logger.PARAM_FULL_CLASS_NAME + " " + Logger.PARAM_METHOD_NAME);
        LoggerConfig loggerConfig = new LoggerConfig()
                .setTag(Logger.PARAM_CODE_LINE)
                .useANRWatchDog(new LoggableANRWatchDog(2000).preventCrash(true))
                .catchUncaughtExceptions()
                .setSeparator(Logger.PARAM_SPACE)
                .setLogThrowableWithStackTrace(true)
                .setThrowableSeparator(Logger.PARAM_NEW_LINE)
                .setLevel(Level.VERBOSE);

        logger.setConfig(logcatLoggerConfig);
        Logger.setBaseConfig(loggerConfig);

        Logger.error("start");
        try{
            Integer.parseInt("a");
        }catch (Exception e){
            Logger.error(e);
        }
        Logger.getBaseConfig().removeLogger(LoggerConfig.DEFAULT_LOGGER);
        logger.e("test","trace will be printed");
        logger.t();
        logger.e("test", "end");
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
            Logger.getBaseConfig().removeLogger("logcat");
            ((Button) v).setText("Add LogcatLogger");
            logger.d("logcat disabled");
        } else {
            Logger.getBaseConfig().addLogger(new LogcatLogger(), "logcat");
            ((Button) v).setText("Remove LogcatLogger");
            logger.d("logcat enabled");
        }
        isLogcatAdded = !isLogcatAdded;
    }

    public void sendToToast(View v) {
        if (isToastAdded) {
            Logger.getBaseConfig().removeLogger("toast");
            ((Button) v).setText("Add ToastLogger");
            logger.d("toast disabled");
        } else {
            Logger.getBaseConfig().addLogger(new ToastLogger(getApplicationContext()), "toast");
            ((Button) v).setText("Remove ToastLogger");
            logger.d("toast enabled");
        }
        isToastAdded = !isToastAdded;
    }

    public void sendTextView(View v) {
        if (isTextViewAdded) {
            Logger.getBaseConfig().removeLogger("textview");
            ((Button) v).setText("Add TextViewLogger");
            logger.d("textView disabled");
        } else {
            Logger.getBaseConfig().addLogger(new TextViewLogger(logTextView), "textview");
            ((Button) v).setText("Remove TextViewLogger");
            logger.d("textView enabled");
        }
        isTextViewAdded = !isTextViewAdded;
    }

    private String getText() {
        return editText.getText().toString();
    }
}
