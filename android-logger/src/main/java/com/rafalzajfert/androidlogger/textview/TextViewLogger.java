package com.rafalzajfert.androidlogger.textview;

import android.widget.TextView;

import com.rafalzajfert.androidlogger.Level;
import com.rafalzajfert.androidlogger.StandardLogger;

/**
 * {@link com.rafalzajfert.androidlogger.Logger Logger} that send messages to Logcat
 * console
 *
 * @author Rafal Zajfert
 * @version 1.0.1 (15/04/2015)
 */
@SuppressWarnings("unused")
public class TextViewLogger extends StandardLogger {

    private final TextViewLoggerConfig config = new TextViewLoggerConfig();

    private final TextView textView;

    public TextViewLogger(TextView textView) {
        this.textView = textView;
    }

    /**
     * Returns Logger configuration
     */
    public TextViewLoggerConfig config() {
        return this.config;
    }

    @Override
    protected String getTag() {
        if (config.tag == null) {
            return super.getTag();
        } else {
            return formatTag(config.getTag());
        }
    }

    @Override
    protected boolean canLogMessage(Level level) {
        return config.isEnabled() && config.isLevelAllowed(level);
    }

    @Override
    protected void print(Level level, String tag, String message) {
        String type = "";
        switch (level) {
            case ERROR:
                type = "E";
                break;
            case INFO:
                type = "I";
                break;
            case DEBUG:
                type = "D";
                break;
            case VERBOSE:
                type = "V";
                break;
            case WARNING:
                type = "W";
                break;
        }
        append(type, tag, message);
    }

    private void append(String type, String tag, String message) {
        String messageSeparator = getMessageSeparator();
        switch (config.method) {
            case APPEND:
                textView.append((textView.length() > 0 ? messageSeparator : "") + type + " " + tag + " " + message);
                break;
            case OVERWRITE:
                textView.setText(type + " " + tag + " " + message);
                break;
            case APPEND_START:
                textView.setText(type + " " + tag + " " + message + (textView.length() > 0 ? messageSeparator
                        : "") + textView.getText());
                break;
        }
    }

    private String getMessageSeparator() {
        return config.eachInNewLine ? "\n" : " ";
    }

}
