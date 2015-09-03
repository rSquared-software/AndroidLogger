package com.rafalzajfert.logger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rafalzajfert.androidlogger.ANRWatchDog;
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
                .removeLogger(LoggerConfig.DEFAULT_LOGGER)
                .setTag(Logger.PARAM_CODE_LINE)
                .useANRWatchDog(new ANRWatchDog(2000).preventCrash(true))
                .catchUncaughtExceptions()
                .setSeparator(Logger.PARAM_SPACE)
                .setThrowableSeparator(Logger.PARAM_NEW_LINE)
                .setLevel(Level.VERBOSE);

        logger.setConfig(logcatLoggerConfig);
        Logger.setBaseConfig(loggerConfig);

        Logger.error("start");
        logger.e("test");
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
            throw new RuntimeException("asd");
        }
        isTextViewAdded = !isTextViewAdded;
    }

    private String getText() {
        return editText.getText().toString();
    }
}
