package com.rafalzajfert.logger.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.Logger;
import com.rafalzajfert.androidlogger.LoggerANRWatchDog;
import com.rafalzajfert.androidlogger.logcat.LogcatLogger;
import com.rafalzajfert.androidlogger.textview.TextViewLogger;
import com.rafalzajfert.androidlogger.toast.ToastLogger;

public class MainActivity extends ActionBarActivity {

	private EditText editText;
	private Spinner typeSpinner;
	private TextView logTextView;

	private boolean isLogcatAdded = false;
	private boolean isToastAdded = false;
	private boolean isTextViewAdded = false;
	private LogcatLogger logger = new LogcatLogger();
	private LogcatLogger l;

	double r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.log_text);
		typeSpinner = (Spinner) findViewById(R.id.log_type);
		logTextView = (TextView) findViewById(R.id.log);
		logger.config().tag(Logger.PARAM_CLASS_NAME + " " + Logger
				.PARAM_METHOD_NAME);

		Logger.globalConfig()
				.tag(Logger.PARAM_CLASS_NAME + "("+Logger.PARAM_LINE_NUMBER+")")
				.startANRWatchDog(new LoggerANRWatchDog(2000).preventCrash(true))
				.catchAllExceptions()
				.enabled(true)
				.logLevel(Level.VERBOSE);
		Logger.error("start");
		int i =0;
		while (i<10000){
			i++;
		}
		l.d("");
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
			Logger.globalConfig().removeLogger("logcat");
			((Button) v).setText("Add LogcatLogger");
			logger.d("logcat enabled");
		} else {
			Logger.globalConfig().addLogger("logcat", new LogcatLogger());
			((Button) v).setText("Remove LogcatLogger");
			logger.d("logcat disabled");
		}
		isLogcatAdded = !isLogcatAdded;
	}

	public void sendToToast(View v) {
		if (isToastAdded) {
			Logger.globalConfig().removeLogger("toast");
			((Button) v).setText("Add ToastLogger");
			logger.d("toast enabled");
		} else {
			Logger.globalConfig().addLogger("toast", new ToastLogger(getApplicationContext()));
			((Button) v).setText("Remove ToastLogger");
			logger.d("toast disabled");
		}
		isToastAdded = !isToastAdded;
	}

	public void sendTextView(View v) {
		if (isTextViewAdded) {
			Logger.globalConfig().removeLogger("textview");
			((Button) v).setText("Add TextViewLogger");
			logger.d("textView enabled");
		} else {
			Logger.globalConfig().addLogger("textview", new TextViewLogger(logTextView));
			((Button) v).setText("Remove TextViewLogger");
			logger.d("textView disabled");
		}
		isTextViewAdded = !isTextViewAdded;
	}

	private String getText() {
		return editText.getText().toString();
	}
}
