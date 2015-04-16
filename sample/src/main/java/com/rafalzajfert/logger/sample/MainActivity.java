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
import com.rafalzajfert.androidlogger.LoggerConfig;
import com.rafalzajfert.androidlogger.logcat.LogCatLogger;
import com.rafalzajfert.androidlogger.logcat.LogCatLoggerConfig;
import com.rafalzajfert.androidlogger.textview.TextViewLogger;
import com.rafalzajfert.androidlogger.toast.ToastLogger;

public class MainActivity extends ActionBarActivity {

	private EditText editText;
	private Spinner typeSpinner;
	private TextView logTextView;

	private boolean isLogCatAdded = false;
	private boolean isToastAdded = false;
	private boolean isTextViewAdded = false;
	LogCatLogger logger = new LogCatLogger();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.log_text);
		typeSpinner = (Spinner) findViewById(R.id.log_type);
		logTextView = (TextView) findViewById(R.id.log);
		logger.setConfiguration(new LogCatLoggerConfig.Builder()
				.tag(Logger.PARAM_SIMPLE_CLASS_NAME +" "+ Logger.PARAM_METHOD_NAME).build());
		Logger.setGlobalConfiguration(new LoggerConfig.Builder().enabled(true).logLevel(Level.VERBOSE).build());
		logger.d("test");
	}

	public void sendToAll(View v) {
		switch (typeSpinner.getSelectedItemPosition()){
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

	public void sendToLogCat(View v) {
		if (isLogCatAdded){
			Logger.getGlobalConfiguration().removeLogger("logcat");
			((Button)v).setText("Add LogCatLogger");
			logger.d("logcat enabled");
		} else {
			Logger.getGlobalConfiguration().addLogger("logcat", new LogCatLogger());
			((Button)v).setText("Remove LogCatLogger");
			logger.d("logcat disabled");
		}
		isLogCatAdded = !isLogCatAdded;
	}

	public void sendToToast(View v) {
		if (isToastAdded){
			Logger.getGlobalConfiguration().removeLogger("toast");
			((Button)v).setText("Add ToastLogger");
			logger.d("toast enabled");
		} else {
			Logger.getGlobalConfiguration().addLogger("toast", new ToastLogger(getApplicationContext()));
			((Button)v).setText("Remove ToastLogger");
			logger.d("toast disabled");
		}
		isToastAdded = !isToastAdded;
	}

	public void sendTextView(View v) {
		if (isTextViewAdded){
			Logger.getGlobalConfiguration().removeLogger("textview");
			((Button)v).setText("Add TextViewLogger");
			logger.d("textView enabled");
		} else {
			Logger.getGlobalConfiguration().addLogger("textview", new TextViewLogger(logTextView));
			((Button)v).setText("Remove TextViewLogger");
			logger.d("textView disabled");
		}
		isTextViewAdded = !isTextViewAdded;
	}

	private String getText(){
		return editText.getText().toString();
	}
}
